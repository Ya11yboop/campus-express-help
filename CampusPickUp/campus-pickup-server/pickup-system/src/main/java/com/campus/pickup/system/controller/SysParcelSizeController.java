package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.system.domain.SysParcelSize;
import com.campus.pickup.system.service.ISysParcelSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 快递大小分类管理控制器（基础数据管理员）
 */
@RestController
@RequestMapping("/system/size")
public class SysParcelSizeController extends BaseController {

    @Autowired
    private ISysParcelSizeService sizeService;

    /** 下拉选项（所有登录用户可访问，用于下单选择） */
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        return AjaxResult.success(sizeService.selectSizeAll());
    }

    @RequiresRole("base_admin")
    @GetMapping("/list")
    public AjaxResult list(SysParcelSize size) {
        startPage();
        List<SysParcelSize> list = sizeService.selectSizeList(size);
        return getDataTable(list);
    }

    @RequiresRole("base_admin")
    @GetMapping("/{sizeId}")
    public AjaxResult getInfo(@PathVariable Long sizeId) {
        return AjaxResult.success(sizeService.selectSizeById(sizeId));
    }

    @RequiresRole("base_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysParcelSize size) {
        return toAjax(sizeService.insertSize(size));
    }

    @RequiresRole("base_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysParcelSize size) {
        return toAjax(sizeService.updateSize(size));
    }

    @RequiresRole("base_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += sizeService.deleteSizeById(id);
        }
        return toAjax(total);
    }
}
