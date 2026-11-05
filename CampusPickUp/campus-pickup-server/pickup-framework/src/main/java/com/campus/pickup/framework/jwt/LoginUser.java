package com.campus.pickup.framework.jwt;

import lombok.Data;

import java.io.Serializable;
import java.util.Set;

/**
 * 登录用户信息（存入 Redis）
 *
 * @author campus-pickup
 */
@Data
public class LoginUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户 ID */
    private Long userId;

    /** 用户名 */
    private String username;

    /** 用户类型（admin/courier/user） */
    private String userType;

    /** 学校 ID（校区管理员绑定学校，数据权限用） */
    private Long schoolId;

    /** 角色权限 key 集合 */
    private Set<String> roles;

    /** 登录令牌唯一标识 */
    private String uuid;

    /** 登录时间 */
    private Long loginTime;

    /** 过期时间 */
    private Long expireTime;
}
