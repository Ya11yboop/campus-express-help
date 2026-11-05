package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.system.domain.SysDictData;
import com.campus.pickup.system.domain.SysDictType;
import com.campus.pickup.system.service.ISysDictService;
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
 * 数据字典管理控制器（基础数据管理员）
 */
@RestController
@RequestMapping("/system/dict")
public class SysDictController extends BaseController {

    @Autowired
    private ISysDictService dictService;

    // ---------------- 字典类型 ----------------

    @RequiresRole("base_admin")
    @GetMapping("/type/list")
    public AjaxResult typeList(SysDictType dictType) {
        startPage();
        List<SysDictType> list = dictService.selectDictTypeList(dictType);
        return getDataTable(list);
    }

    @RequiresRole("base_admin")
    @GetMapping("/type/{dictId}")
    public AjaxResult typeInfo(@PathVariable Long dictId) {
        return AjaxResult.success(dictService.selectDictTypeById(dictId));
    }

    @RequiresRole("base_admin")
    @PostMapping("/type")
    public AjaxResult typeAdd(@RequestBody SysDictType dictType) {
        return toAjax(dictService.insertDictType(dictType));
    }

    @RequiresRole("base_admin")
    @PutMapping("/type")
    public AjaxResult typeEdit(@RequestBody SysDictType dictType) {
        return toAjax(dictService.updateDictType(dictType));
    }

    @RequiresRole("base_admin")
    @DeleteMapping("/type/{ids}")
    public AjaxResult typeRemove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += dictService.deleteDictTypeById(id);
        }
        return toAjax(total);
    }

    // ---------------- 字典数据 ----------------

    @RequiresRole("base_admin")
    @GetMapping("/data/list")
    public AjaxResult dataList(SysDictData dictData) {
        startPage();
        List<SysDictData> list = dictService.selectDictDataList(dictData);
        return getDataTable(list);
    }

    /** 按类型查字典数据（所有登录用户可访问，用于前端下拉） */
    @GetMapping("/data/type/{dictType}")
    public AjaxResult dataByType(@PathVariable String dictType) {
        return AjaxResult.success(dictService.selectDictDataByType(dictType));
    }

    @RequiresRole("base_admin")
    @GetMapping("/data/{dictCode}")
    public AjaxResult dataInfo(@PathVariable Long dictCode) {
        return AjaxResult.success(dictService.selectDictDataById(dictCode));
    }

    @RequiresRole("base_admin")
    @PostMapping("/data")
    public AjaxResult dataAdd(@RequestBody SysDictData dictData) {
        return toAjax(dictService.insertDictData(dictData));
    }

    @RequiresRole("base_admin")
    @PutMapping("/data")
    public AjaxResult dataEdit(@RequestBody SysDictData dictData) {
        return toAjax(dictService.updateDictData(dictData));
    }

    @RequiresRole("base_admin")
    @DeleteMapping("/data/{ids}")
    public AjaxResult dataRemove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += dictService.deleteDictDataById(id);
        }
        return toAjax(total);
    }
}
