package com.campus.pickup.system.controller;

import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.common.utils.StringUtils;
import com.campus.pickup.framework.jwt.LoginUser;
import com.campus.pickup.framework.jwt.TokenService;
import com.campus.pickup.system.domain.LoginBody;
import com.campus.pickup.system.domain.SysUser;
import com.campus.pickup.system.service.ISysLoginService;
import com.campus.pickup.system.service.ISysRoleService;
import com.campus.pickup.system.service.ISysUserService;
import com.campus.pickup.system.util.UserProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 登录控制器
 *
 * @author campus-pickup
 */
@RestController
public class SysLoginController {

    @Autowired
    private ISysLoginService loginService;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private TokenService tokenService;

    @Value("${wechat.appid:}")
    private String wechatAppid;

    @Value("${wechat.secret:}")
    private String wechatSecret;

    @PostMapping("/login")
    public AjaxResult login(@RequestBody LoginBody loginBody) {
        String token = loginService.login(loginBody.getUsername(), loginBody.getPassword());
        LoginUser loginUser = tokenService.getLoginUser(token);
        AjaxResult result = AjaxResult.success("登录成功");
        result.put("token", token);
        result.put("userType", loginUser != null ? loginUser.getUserType() : null);
        result.put("roles", loginUser != null ? loginUser.getRoles() : null);
        return result;
    }

    @PostMapping("/logout")
    public AjaxResult logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        if (token != null && token.startsWith("Bearer ")) {
            tokenService.delLoginUser(token.substring(7));
        }
        return AjaxResult.success("退出成功");
    }

    /**
     * 微信一键登录：用 wx.login 的 code 换取 openid，首次登录自动注册
     */
    @PostMapping("/login/wechat")
    public AjaxResult wechatLogin(@RequestBody Map<String, Object> body) {
        String code = body == null ? null : (body.get("code") == null ? null : String.valueOf(body.get("code")));
        if (StringUtils.isEmpty(code)) {
            return AjaxResult.error("缺少微信登录 code");
        }
        String openid = code2session(code);
        if (StringUtils.isEmpty(openid)) {
            return AjaxResult.error("微信登录失败，请检查 AppID/AppSecret 配置");
        }

        SysUser user = userService.selectUserByOpenId(openid);
        boolean newUser = false;
        if (user == null) {
            // 首次登录：自动注册（用户名先用占位，完善信息时再填写真实用户名）
            user = new SysUser();
            user.setOpenid(openid);
            user.setUserName("wx" + openid);
            user.setUserType("user");
            user.setStatus("0");
            user.setCreditScore(100);
            userService.insertUser(user);
            Long userRoleId = roleService.selectRoleIdByKey("user");
            if (userRoleId != null) {
                userService.insertUserRole(user.getUserId(), userRoleId);
            }
            newUser = true;
        }

        if ("1".equals(user.getStatus())) {
            return AjaxResult.error("账号已停用");
        }

        String token = createToken(user);
        boolean complete = UserProfileUtil.isComplete(user);
        AjaxResult result = AjaxResult.success("登录成功");
        result.put("token", token);
        result.put("needComplete", !complete);
        result.put("missing", UserProfileUtil.missingFields(user));
        result.put("message", complete ? "" : UserProfileUtil.buildMessage(user));
        result.put("newUser", newUser);
        return result;
    }

    private String createToken(SysUser user) {
        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());
        loginUser.setUserType(user.getUserType());
        loginUser.setSchoolId(user.getSchoolId());
        loginUser.setRoles(roleService.selectRoleKeysByUserId(user.getUserId()));
        return tokenService.createToken(loginUser);
    }

    private String code2session(String code) {
        try {
            String url = "https://api.weixin.qq.com/sns/jscode2session?appid=" + wechatAppid
                    + "&secret=" + wechatSecret
                    + "&js_code=" + code
                    + "&grant_type=authorization_code";
            String resp = new RestTemplate().getForObject(url, String.class);
            JSONObject json = JSON.parseObject(resp);
            if (json == null) {
                return null;
            }
            String openid = json.getString("openid");
            return StringUtils.isEmpty(openid) ? null : openid;
        } catch (Exception e) {
            return null;
        }
    }
}
