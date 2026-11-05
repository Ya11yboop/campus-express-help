package com.campus.pickup.framework.context;

import com.campus.pickup.framework.jwt.LoginUser;

import java.util.Set;

/**
 * 当前登录用户上下文（基于 ThreadLocal）
 *
 * @author campus-pickup
 */
public class UserContext {

    private static final ThreadLocal<LoginUser> HOLDER = new ThreadLocal<>();

    public static void set(LoginUser loginUser) {
        HOLDER.set(loginUser);
    }

    public static LoginUser get() {
        return HOLDER.get();
    }

    public static Long getUserId() {
        LoginUser user = get();
        return user == null ? null : user.getUserId();
    }

    public static String getUsername() {
        LoginUser user = get();
        return user == null ? null : user.getUsername();
    }

    public static String getUserType() {
        LoginUser user = get();
        return user == null ? null : user.getUserType();
    }

    public static Long getSchoolId() {
        LoginUser user = get();
        return user == null ? null : user.getSchoolId();
    }

    /**
     * 是否校区/学院管理员（数据权限：只能访问本校数据）。
     *
     * 所有以 campus_ 开头的角色都属于校区管理员，例如：
     * campus_admin（学院管理员）、campus_operation_admin（学院运营管理员）、
     * campus_cs_admin（学院客服管理员）、campus_base_admin（学院基础数据管理员）。
     *
     * 注意：这里不能用 {@link #hasRole(String)} 判断，因为 hasRole 对 super_admin / campus_admin
     * 做了短路，会导致超级管理员被误判为校区管理员，从而把前端传入的学校筛选参数覆盖掉。
     */
    public static boolean isCampusAdmin() {
        LoginUser user = get();
        if (user == null || user.getRoles() == null) {
            return false;
        }
        for (String role : user.getRoles()) {
            if (role.startsWith("campus_")) {
                return true;
            }
        }
        return false;
    }

    public static Set<String> getRoles() {
        LoginUser user = get();
        return user == null ? null : user.getRoles();
    }

    /**
     * 判断当前用户是否拥有某角色。
     *
     * 权限规则：
     * - super_admin 与 campus_admin 拥有全部模块权限；
     * - campus_xxx 与对应的 xxx 角色权限一致（如 campus_operation_admin 等同 operation_admin）。
     */
    public static boolean hasRole(String roleKey) {
        LoginUser user = get();
        if (user == null || user.getRoles() == null) {
            return false;
        }
        if (user.getRoles().contains("super_admin") || user.getRoles().contains("campus_admin")) {
            return true;
        }
        return user.getRoles().contains(roleKey) || user.getRoles().contains("campus_" + roleKey);
    }

    public static void clear() {
        HOLDER.remove();
    }
}
