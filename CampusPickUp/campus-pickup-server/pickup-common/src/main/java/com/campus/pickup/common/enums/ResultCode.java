package com.campus.pickup.common.enums;

/**
 * 统一返回结果与业务异常消息常量枚举
 *
 * @author campus-pickup
 */
public enum ResultCode {

    // ==================== 通用 ====================
    SUCCESS(200, "操作成功"),
    ERROR(500, "操作失败"),
    UNAUTHORIZED(401, "未登录或登录已过期"),
    FORBIDDEN(403, "无权限访问该功能"),
    NOT_FOUND(404, "资源不存在"),

    // ==================== 登录 / 认证 ====================
    LOGIN_SUCCESS(200, "登录成功"),
    USERNAME_OR_PASSWORD_EMPTY(500, "用户名或密码不能为空"),
    USERNAME_NOT_FOUND(500, "用户名错误，该用户不存在"),
    PASSWORD_ERROR(500, "密码错误"),
    ACCOUNT_DISABLED(500, "账号已停用"),
    LOGIN_EXPIRED(401, "登录已过期，请重新登录"),

    // ==================== 业务 ====================
    USER_NOT_EXIST(500, "用户不存在");

    private final int code;
    private final String message;

    ResultCode(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
