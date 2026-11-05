package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysExpressPoint;
import com.campus.pickup.system.service.ISysExpressPointService;
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
 * 驿站管理控制器（基础数据管理员）
 */
@RestController
@RequestMapping("/system/point")
public class SysExpressPointController extends BaseController {

    @Autowired
    private ISysExpressPointService pointService;

    /** 下拉选项（所有登录用户可访问，用于下单选择；仅返回当前用户所属学校的驿站） */
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        Long schoolId = UserContext.getSchoolId();
        return AjaxResult.success(pointService.selectPointAll(schoolId));
    }

    @RequiresRole("base_admin")
    @GetMapping("/list")
    public AjaxResult list(SysExpressPoint point) {
        if (UserContext.isCampusAdmin()) {
            point.setSchoolId(UserContext.getSchoolId());
        }
        startPage();
        List<SysExpressPoint> list = pointService.selectPointList(point);
        return getDataTable(list);
    }

    @RequiresRole("base_admin")
    @GetMapping("/{pointId}")
    public AjaxResult getInfo(@PathVariable Long pointId) {
        return AjaxResult.success(pointService.selectPointById(pointId));
    }

    @RequiresRole("base_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysExpressPoint point) {
        return toAjax(pointService.insertPoint(point));
    }

    @RequiresRole("base_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysExpressPoint point) {
        return toAjax(pointService.updatePoint(point));
    }

    @RequiresRole("base_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += pointService.deletePointById(id);
        }
        return toAjax(total);
    }
}
