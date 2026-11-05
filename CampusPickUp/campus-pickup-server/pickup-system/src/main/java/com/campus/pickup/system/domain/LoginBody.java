package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;

/**
 * 登录请求体
 *
 * @author campus-pickup
 */
@Data
public class LoginBody implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 验证码（预留） */
    private String code;

    /** 验证码唯一标识（预留） */
    private String uuid;
}
