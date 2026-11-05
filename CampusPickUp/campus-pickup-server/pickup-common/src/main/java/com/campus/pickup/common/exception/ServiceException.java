package com.campus.pickup.common.exception;

import com.campus.pickup.common.enums.ResultCode;

/**
 * 业务异常
 *
 * @author campus-pickup
 */
public class ServiceException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    /** 错误码 */
    private final Integer code;

    /** 错误提示 */
    private final String message;

    /**
     * 使用常量枚举构造（推荐）
     */
    public ServiceException(ResultCode resultCode) {
        super(resultCode.getMessage());
        this.code = resultCode.getCode();
        this.message = resultCode.getMessage();
    }

    /**
     * 使用自定义消息构造（默认错误码 500）
     */
    public ServiceException(String message) {
        super(message);
        this.code = ResultCode.ERROR.getCode();
        this.message = message;
    }

    public Integer getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }
}
