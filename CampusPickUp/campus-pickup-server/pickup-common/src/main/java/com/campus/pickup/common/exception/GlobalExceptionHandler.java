package com.campus.pickup.common.exception;

import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.common.enums.ResultCode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * 全局异常处理器
 *
 * @author campus-pickup
 */
@RestControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    /**
     * 业务异常
     */
    @ExceptionHandler(ServiceException.class)
    public AjaxResult handleServiceException(ServiceException e) {
        log.error("【业务异常】code={}, message={}", e.getCode(), e.getMessage(), e);
        AjaxResult result = AjaxResult.error(e.getCode(), e.getMessage());
        log.error("【业务异常响应】code={}, msg={}", result.get("code"), result.get("msg"));
        return result;
    }

    /**
     * 系统异常
     */
    @ExceptionHandler(Exception.class)
    public AjaxResult handleException(Exception e) {
        log.error("【系统异常】{}", e.getMessage(), e);
        AjaxResult result = AjaxResult.error(ResultCode.ERROR.getCode(), "服务器内部错误：" + e.getMessage());
        log.error("【系统异常响应】code={}, msg={}", result.get("code"), result.get("msg"));
        return result;
    }
}
