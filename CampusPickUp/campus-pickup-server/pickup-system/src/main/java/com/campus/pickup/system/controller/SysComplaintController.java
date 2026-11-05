package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysComplaint;
import com.campus.pickup.system.service.ISysComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Date;
import java.util.List;

/**
 * 申诉/投诉控制器
 */
@RestController
@RequestMapping("/complaint")
public class SysComplaintController extends BaseController {

    @Autowired
    private ISysComplaintService complaintService;

    /** 发起申诉/投诉（所有登录用户） */
    @PostMapping
    public AjaxResult create(@RequestBody SysComplaint complaint) {
        complaint.setUserId(UserContext.getUserId());
        complaint.setHandleStatus("0");
        complaintService.insertComplaint(complaint);
        return AjaxResult.success("提交成功");
    }

    /** 我的申诉/投诉（当前登录用户） */
    @GetMapping("/my")
    public AjaxResult my() {
        SysComplaint complaint = new SysComplaint();
        complaint.setUserId(UserContext.getUserId());
        return AjaxResult.success(complaintService.selectComplaintList(complaint));
    }

    @RequiresRole({"cs_admin", "operation_admin"})
    @GetMapping("/list")
    public AjaxResult list(SysComplaint complaint) {
        if (UserContext.isCampusAdmin()) {
            complaint.setSchoolId(UserContext.getSchoolId());
        }
        startPage();
        List<SysComplaint> list = complaintService.selectComplaintList(complaint);
        return getDataTable(list);
    }

    @RequiresRole({"cs_admin", "operation_admin"})
    @GetMapping("/{complaintId}")
    public AjaxResult getInfo(@PathVariable Long complaintId) {
        return AjaxResult.success(complaintService.selectComplaintById(complaintId));
    }

    /** 处理（客服上传审核结果） */
    @RequiresRole("cs_admin")
    @PutMapping("/handle")
    public AjaxResult handle(@RequestBody SysComplaint complaint) {
        complaint.setHandlerId(UserContext.getUserId());
        complaint.setHandleTime(new Date());
        complaint.setHandleStatus("1");
        return toAjax(complaintService.handleComplaint(complaint));
    }

    @RequiresRole("operation_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += complaintService.deleteComplaintById(id);
        }
        return toAjax(total);
    }
}
