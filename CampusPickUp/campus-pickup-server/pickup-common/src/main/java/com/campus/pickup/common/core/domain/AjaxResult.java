package com.campus.pickup.common.core.domain;

import com.campus.pickup.common.enums.ResultCode;
import com.campus.pickup.common.utils.StringUtils;

import java.io.Serializable;
import java.util.HashMap;

/**
 * 统一返回结果
 *
 * @author campus-pickup
 */
public class AjaxResult extends HashMap<String, Object> implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 状态码 */
    public static final String CODE_TAG = "code";

    /** 返回内容 */
    public static final String MSG_TAG = "msg";

    /** 数据对象 */
    public static final String DATA_TAG = "data";

    public AjaxResult() {
    }

    public AjaxResult(int code, String msg) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
    }

    public AjaxResult(int code, String msg, Object data) {
        super.put(CODE_TAG, code);
        super.put(MSG_TAG, msg);
        if (StringUtils.isNotNull(data)) {
            super.put(DATA_TAG, data);
        }
    }

    public static AjaxResult success() {
        return AjaxResult.success(ResultCode.SUCCESS.getMessage());
    }

    public static AjaxResult success(Object data) {
        return AjaxResult.success(ResultCode.SUCCESS.getMessage(), data);
    }

    public static AjaxResult success(String msg) {
        return AjaxResult.success(msg, null);
    }

    public static AjaxResult success(String msg, Object data) {
        return new AjaxResult(ResultCode.SUCCESS.getCode(), msg, data);
    }

    public static AjaxResult error() {
        return AjaxResult.error(ResultCode.ERROR.getMessage());
    }

    public static AjaxResult error(String msg) {
        return AjaxResult.error(msg, null);
    }

    public static AjaxResult error(String msg, Object data) {
        return new AjaxResult(ResultCode.ERROR.getCode(), msg, data);
    }

    public static AjaxResult error(int code, String msg) {
        return new AjaxResult(code, msg, null);
    }

    public boolean isSuccess() {
        return ResultCode.SUCCESS.getCode() == (int) this.get(CODE_TAG);
    }

    @SuppressWarnings("unchecked")
    public <T> T get(Class<T> clazz) {
        return (T) this.get(DATA_TAG);
    }

    @Override
    public AjaxResult put(String key, Object value) {
        super.put(key, value);
        return this;
    }

    public static AjaxResult toAjax(boolean rows) {
        return rows ? success() : error();
    }

    public static AjaxResult toAjax(int rows) {
        return rows > 0 ? success() : error();
    }
}
