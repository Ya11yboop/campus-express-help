package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysDormitory;
import com.campus.pickup.system.service.ISysDormitoryService;
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
 * 宿舍楼管理控制器（基础数据管理员）
 */
@RestController
@RequestMapping("/system/dormitory")
public class SysDormitoryController extends BaseController {

    @Autowired
    private ISysDormitoryService dormitoryService;

    /** 下拉选项（所有登录用户可访问，用于下单选择） */
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        Long schoolId = UserContext.isCampusAdmin() ? UserContext.getSchoolId() : null;
        return AjaxResult.success(dormitoryService.selectDormitoryAll(schoolId));
    }

    @RequiresRole("base_admin")
    @GetMapping("/list")
    public AjaxResult list(SysDormitory dormitory) {
        if (UserContext.isCampusAdmin()) {
            dormitory.setSchoolId(UserContext.getSchoolId());
        }
        startPage();
        List<SysDormitory> list = dormitoryService.selectDormitoryList(dormitory);
        return getDataTable(list);
    }

    @RequiresRole("base_admin")
    @GetMapping("/{dormitoryId}")
    public AjaxResult getInfo(@PathVariable Long dormitoryId) {
        return AjaxResult.success(dormitoryService.selectDormitoryById(dormitoryId));
    }

    @RequiresRole("base_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysDormitory dormitory) {
        return toAjax(dormitoryService.insertDormitory(dormitory));
    }

    @RequiresRole("base_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysDormitory dormitory) {
        return toAjax(dormitoryService.updateDormitory(dormitory));
    }

    @RequiresRole("base_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += dormitoryService.deleteDormitoryById(id);
        }
        return toAjax(total);
    }
}
