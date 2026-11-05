package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysEvaluate;
import com.campus.pickup.system.service.ISysEvaluateService;
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
 * 评价控制器
 */
@RestController
@RequestMapping("/evaluate")
public class SysEvaluateController extends BaseController {

    @Autowired
    private ISysEvaluateService evaluateService;

    /** 发表评价（所有登录用户） */
    @PostMapping
    public AjaxResult create(@RequestBody SysEvaluate evaluate) {
        evaluate.setUserId(UserContext.getUserId());
        evaluateService.insertEvaluate(evaluate);
        return AjaxResult.success("评价成功");
    }

    /** 我的评价（当前登录用户） */
    @GetMapping("/my")
    public AjaxResult my() {
        SysEvaluate evaluate = new SysEvaluate();
        evaluate.setUserId(UserContext.getUserId());
        return AjaxResult.success(evaluateService.selectEvaluateList(evaluate));
    }

    @RequiresRole({"cs_admin", "operation_admin"})
    @GetMapping("/list")
    public AjaxResult list(SysEvaluate evaluate) {
        if (UserContext.isCampusAdmin()) {
            evaluate.setSchoolId(UserContext.getSchoolId());
        }
        startPage();
        List<SysEvaluate> list = evaluateService.selectEvaluateList(evaluate);
        return getDataTable(list);
    }

    @RequiresRole({"cs_admin", "operation_admin"})
    @GetMapping("/{evaluateId}")
    public AjaxResult getInfo(@PathVariable Long evaluateId) {
        return AjaxResult.success(evaluateService.selectEvaluateById(evaluateId));
    }

    /** 客服回复 */
    @RequiresRole("cs_admin")
    @PutMapping("/reply")
    public AjaxResult reply(@RequestBody SysEvaluate evaluate) {
        return toAjax(evaluateService.replyEvaluate(evaluate));
    }

    @RequiresRole("operation_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += evaluateService.deleteEvaluateById(id);
        }
        return toAjax(total);
    }
}
