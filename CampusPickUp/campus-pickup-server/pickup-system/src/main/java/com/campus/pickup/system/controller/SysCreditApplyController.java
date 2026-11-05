package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysCreditApply;
import com.campus.pickup.system.service.ISysCreditApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 信誉分扣除申请控制器
 */
@RestController
@RequestMapping("/credit/apply")
public class SysCreditApplyController extends BaseController {

    @Autowired
    private ISysCreditApplyService applyService;

    /** 客服提交扣除申请 */
    @RequiresRole("cs_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysCreditApply apply) {
        if (apply.getUserId() == null) {
            return AjaxResult.error("请选择用户");
        }
        if (apply.getChangeScore() == null || apply.getChangeScore() >= 0) {
            return AjaxResult.error("扣除分数必须为负数");
        }
        apply.setApplyId(null);
        apply.setApplyUserId(UserContext.getUserId());
        apply.setApplyStatus("0");
        return toAjax(applyService.insert(apply));
    }

    /** 申请列表（客服/运营/超管可见） */
    @RequiresRole({"super_admin", "operation_admin", "cs_admin"})
    @GetMapping("/list")
    public AjaxResult list(SysCreditApply apply) {
        startPage();
        List<SysCreditApply> list = applyService.selectList(apply);
        return getDataTable(list);
    }

    /** 审核（通过/驳回，超管或管理员） */
    @RequiresRole({"super_admin", "operation_admin"})
    @PutMapping("/audit/{applyId}")
    public AjaxResult audit(@PathVariable Long applyId, @RequestBody Map<String, Object> body) {
        boolean approve = Boolean.parseBoolean(String.valueOf(body.get("approve")));
        String remark = body.get("remark") == null ? "" : String.valueOf(body.get("remark"));
        try {
            return toAjax(applyService.audit(applyId, approve, UserContext.getUserId(), remark));
        } catch (ServiceException e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
