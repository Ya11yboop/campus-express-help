package com.campus.pickup.system.service;

/**
 * 登录服务接口
 *
 * @author campus-pickup
 */
public interface ISysLoginService {

    /**
     * 登录，成功返回 JWT 令牌
     */
    String login(String username, String password);
}
