package com.campus.pickup.system.service.impl;

import com.campus.pickup.common.enums.ResultCode;
import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.common.utils.StringUtils;
import com.campus.pickup.framework.jwt.LoginUser;
import com.campus.pickup.framework.jwt.TokenService;
import com.campus.pickup.system.domain.SysUser;
import com.campus.pickup.system.service.ISysLoginService;
import com.campus.pickup.system.service.ISysRoleService;
import com.campus.pickup.system.service.ISysUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;

import java.nio.charset.StandardCharsets;

/**
 * 登录服务实现
 *
 * @author campus-pickup
 */
@Service
public class SysLoginServiceImpl implements ISysLoginService {

    private static final Logger log = LoggerFactory.getLogger(SysLoginServiceImpl.class);

    @Autowired
    private ISysUserService userService;

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private TokenService tokenService;

    @Override
    public String login(String username, String password) {
        log.info("【登录】用户 {} 开始登录", username);

        if (StringUtils.isEmpty(username) || StringUtils.isEmpty(password)) {
            log.warn("【登录】用户名或密码为空");
            throw new ServiceException(ResultCode.USERNAME_OR_PASSWORD_EMPTY);
        }

        SysUser user = userService.selectUserByUserName(username);
        if (user == null) {
            log.warn("【登录】用户名错误，用户不存在：{}", username);
            throw new ServiceException(ResultCode.USERNAME_NOT_FOUND);
        }

        String md5 = DigestUtils.md5DigestAsHex(password.getBytes(StandardCharsets.UTF_8));
        if (!md5.equalsIgnoreCase(user.getPassword())) {
            log.warn("【登录】密码错误：{}", username);
            throw new ServiceException(ResultCode.PASSWORD_ERROR);
        }

        if ("1".equals(user.getStatus())) {
            log.warn("【登录】账号已停用：{}", username);
            throw new ServiceException(ResultCode.ACCOUNT_DISABLED);
        }

        LoginUser loginUser = new LoginUser();
        loginUser.setUserId(user.getUserId());
        loginUser.setUsername(user.getUserName());
        loginUser.setUserType(user.getUserType());
        loginUser.setSchoolId(user.getSchoolId());
        loginUser.setRoles(roleService.selectRoleKeysByUserId(user.getUserId()));
        String token = tokenService.createToken(loginUser);
        log.info("【登录】用户 {} 登录成功", username);
        return token;
    }
}
