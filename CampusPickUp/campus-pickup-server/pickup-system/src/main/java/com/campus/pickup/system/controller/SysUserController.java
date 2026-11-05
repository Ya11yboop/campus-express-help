package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.common.utils.StringUtils;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysUser;
import com.campus.pickup.system.service.ISysUserService;
import com.campus.pickup.system.util.UserProfileUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.DigestUtils;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 用户管理控制器
 *
 * @author campus-pickup
 */
@RestController
@RequestMapping("/system/user")
public class SysUserController extends BaseController {

    @Autowired
    private ISysUserService userService;

    /**
     * 获取当前登录用户信息（所有登录用户可用）
     */
    @GetMapping("/profile")
    public AjaxResult profile() {
        return AjaxResult.success(UserContext.get());
    }

    /**
     * 获取当前登录用户的完整信息（含学号/身份证/信誉分，所有登录用户可用）
     */
    @GetMapping("/profileInfo")
    public AjaxResult profileInfo() {
        return AjaxResult.success(userService.selectUserById(UserContext.getUserId()));
    }

    /**
     * 检查当前登录用户信息是否完善（所有登录用户可用）
     */
    @GetMapping("/checkComplete")
    public AjaxResult checkComplete() {
        SysUser user = userService.selectUserById(UserContext.getUserId());
        boolean complete = UserProfileUtil.isComplete(user);
        AjaxResult result = AjaxResult.success();
        result.put("complete", complete);
        result.put("missing", UserProfileUtil.missingFields(user));
        result.put("message", complete ? "" : UserProfileUtil.buildMessage(user));
        return result;
    }

    /**
     * 完善个人信息（微信首次登录后填写必填项，所有登录用户可用）
     */
    @PostMapping("/complete")
    public AjaxResult complete(@RequestBody SysUser body) {
        Long userId = UserContext.getUserId();
        SysUser user = userService.selectUserById(userId);
        if (user == null) {
            return AjaxResult.error("用户不存在");
        }
        if (StringUtils.isEmpty(body.getUserName())) {
            return AjaxResult.error("用户名不能为空");
        }
        SysUser exist = userService.selectUserByUserName(body.getUserName().trim());
        if (exist != null && !exist.getUserId().equals(userId)) {
            return AjaxResult.error("用户名已存在");
        }
        if (StringUtils.isEmpty(body.getPassword())) {
            return AjaxResult.error("密码不能为空");
        }
        if (StringUtils.isEmpty(body.getRealName())) {
            return AjaxResult.error("姓名不能为空");
        }
        if (StringUtils.isEmpty(body.getPhone())) {
            return AjaxResult.error("电话不能为空");
        }
        if (body.getSchoolId() == null) {
            return AjaxResult.error("请选择学校");
        }
        if (StringUtils.isEmpty(body.getStudentNo())) {
            return AjaxResult.error("学号不能为空");
        }
        if (StringUtils.isEmpty(body.getIdCard())) {
            return AjaxResult.error("身份证不能为空");
        }

        SysUser update = new SysUser();
        update.setUserId(userId);
        update.setUserName(body.getUserName().trim());
        update.setPassword(DigestUtils.md5DigestAsHex(body.getPassword().getBytes(StandardCharsets.UTF_8)));
        update.setRealName(body.getRealName().trim());
        update.setPhone(body.getPhone().trim());
        update.setSchoolId(body.getSchoolId());
        update.setStudentNo(body.getStudentNo().trim());
        update.setIdCard(body.getIdCard().trim());
        userService.updateUser(update);
        return AjaxResult.success("信息完善成功");
    }

    /**
     * 用户下拉选项（客服/运营/超管，用于选择用户，不含敏感字段）
     */
    @RequiresRole({"operation_admin", "cs_admin"})
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        SysUser user = new SysUser();
        if (UserContext.isCampusAdmin()) {
            user.setSchoolId(UserContext.getSchoolId());
        }
        List<SysUser> users = userService.selectUserList(user);
        List<Map<String, Object>> result = new ArrayList<>();
        for (SysUser u : users) {
            Map<String, Object> m = new HashMap<>();
            m.put("userId", u.getUserId());
            m.put("userName", u.getUserName());
            m.put("realName", u.getRealName());
            m.put("nickName", u.getNickName());
            result.add(m);
        }
        return AjaxResult.success(result);
    }

    /**
     * 分页查询用户列表（运营管理员）
     */
    @RequiresRole("operation_admin")
    @GetMapping("/list")
    public AjaxResult list(SysUser user) {
        if (UserContext.isCampusAdmin()) {
            user.setSchoolId(UserContext.getSchoolId());
        }
        startPage();
        List<SysUser> list = userService.selectUserList(user);
        return getDataTable(list);
    }

    /**
     * 根据用户 ID 获取详情（运营管理员）
     */
    @RequiresRole("operation_admin")
    @GetMapping("/{userId}")
    public AjaxResult getInfo(@PathVariable Long userId) {
        return AjaxResult.success(userService.selectUserById(userId));
    }

    /**
     * 新增用户（运营管理员）
     */
    @RequiresRole("operation_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysUser user) {
        if (userService.selectUserByUserName(user.getUserName()) != null) {
            return AjaxResult.error("用户名已存在");
        }
        if (StringUtils.isEmpty(user.getPassword())) {
            user.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
        } else {
            user.setPassword(DigestUtils.md5DigestAsHex(user.getPassword().getBytes(StandardCharsets.UTF_8)));
        }
        if (StringUtils.isEmpty(user.getUserType())) {
            user.setUserType("user");
        }
        if (user.getCreditScore() == null) {
            user.setCreditScore(100);
        }
        if (StringUtils.isEmpty(user.getStatus())) {
            user.setStatus("0");
        }
        return toAjax(userService.insertUser(user));
    }

    /**
     * 修改用户（运营管理员）
     */
    @RequiresRole("operation_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysUser user) {
        return toAjax(userService.updateUser(user));
    }

    /**
     * 删除用户（运营管理员）
     */
    @RequiresRole("operation_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += userService.deleteUserById(id);
        }
        return toAjax(total);
    }
}
