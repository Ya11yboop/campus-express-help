package com.campus.pickup.common.core.controller;

import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.common.utils.ServletUtils;
import com.campus.pickup.common.utils.StringUtils;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;

import java.beans.PropertyEditorSupport;
import java.util.Date;
import java.util.List;

/**
 * Web 层通用数据处理
 *
 * @author campus-pickup
 */
public class BaseController {

    /**
     * 将前台传递过来的日期格式的字符串，自动转化为 Date 类型
     */
    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.registerCustomEditor(Date.class, new PropertyEditorSupport() {
            @Override
            public void setAsText(String text) {
                setValue(StringUtils.parseDate(text));
            }
        });
    }

    /**
     * 设置请求分页数据
     */
    protected void startPage() {
        PageHelper.startPage(ServletUtils.getParameterToInt("pageNum", 1),
                ServletUtils.getParameterToInt("pageSize", 10));
    }

    /**
     * 响应请求分页数据
     */
    @SuppressWarnings({"rawtypes", "unchecked"})
    protected AjaxResult getDataTable(List<?> list) {
        PageInfo pageInfo = new PageInfo(list);
        AjaxResult result = AjaxResult.success();
        result.put("rows", pageInfo.getList());
        result.put("total", pageInfo.getTotal());
        return result;
    }

    /**
     * 响应返回结果
     */
    protected AjaxResult toAjax(int rows) {
        return AjaxResult.toAjax(rows);
    }

    /**
     * 响应返回结果
     */
    protected AjaxResult toAjax(boolean result) {
        return AjaxResult.toAjax(result);
    }
}
