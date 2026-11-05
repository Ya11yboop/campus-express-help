package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.system.domain.SysSchool;
import com.campus.pickup.system.service.ISysSchoolService;
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
 * 学校管理控制器（基础数据管理员）
 */
@RestController
@RequestMapping("/system/school")
public class SysSchoolController extends BaseController {

    @Autowired
    private ISysSchoolService schoolService;

    /** 下拉选项（所有登录用户可访问，用于用户/筛选选择学校） */
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        return AjaxResult.success(schoolService.selectSchoolAll());
    }

    @RequiresRole("base_admin")
    @GetMapping("/list")
    public AjaxResult list(SysSchool school) {
        startPage();
        List<SysSchool> list = schoolService.selectSchoolList(school);
        return getDataTable(list);
    }

    @RequiresRole("base_admin")
    @GetMapping("/{schoolId}")
    public AjaxResult getInfo(@PathVariable Long schoolId) {
        return AjaxResult.success(schoolService.selectSchoolById(schoolId));
    }

    @RequiresRole("base_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysSchool school) {
        return toAjax(schoolService.insertSchool(school));
    }

    @RequiresRole("base_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysSchool school) {
        return toAjax(schoolService.updateSchool(school));
    }

    @RequiresRole("base_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += schoolService.deleteSchoolById(id);
        }
        return toAjax(total);
    }
}
