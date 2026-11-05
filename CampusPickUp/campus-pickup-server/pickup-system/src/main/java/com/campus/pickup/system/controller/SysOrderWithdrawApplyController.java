package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysOrderWithdrawApply;
import com.campus.pickup.system.service.ISysOrderWithdrawApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

/**
 * 接单撤回申请控制器
 */
@RestController
@RequestMapping("/order/withdraw/apply")
public class SysOrderWithdrawApplyController extends BaseController {

    @Autowired
    private ISysOrderWithdrawApplyService applyService;

    /** 申请列表 */
    @RequiresRole({"super_admin", "operation_admin", "cs_admin"})
    @GetMapping("/list")
    public AjaxResult list(SysOrderWithdrawApply apply) {
        startPage();
        List<SysOrderWithdrawApply> list = applyService.selectList(apply);
        return getDataTable(list);
    }

    /** 审核（通过/驳回） */
    @RequiresRole({"super_admin", "operation_admin"})
    @PutMapping("/audit/{applyId}")
    public AjaxResult audit(@PathVariable Long applyId, @RequestBody Map<String, Object> body) {
        boolean approve = Boolean.parseBoolean(String.valueOf(body.get("approve")));
        String remark = body.get("remark") == null ? "" : String.valueOf(body.get("remark"));
        try {
            return toAjax(applyService.audit(applyId, approve, remark, UserContext.getUserId()));
        } catch (ServiceException e) {
            return AjaxResult.error(e.getMessage());
        }
    }
}
