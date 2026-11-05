package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysNotice;
import com.campus.pickup.system.service.ISysNoticeService;
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
 * 公告控制器
 */
@RestController
@RequestMapping("/notice")
public class SysNoticeController extends BaseController {

    @Autowired
    private ISysNoticeService noticeService;

    /** 已发布公告列表（所有登录用户可访问，小程序用） */
    @GetMapping("/listAll")
    public AjaxResult listAll() {
        Long schoolId = UserContext.isCampusAdmin() ? UserContext.getSchoolId() : null;
        return AjaxResult.success(noticeService.selectNoticeAll(schoolId));
    }

    @RequiresRole("operation_admin")
    @GetMapping("/list")
    public AjaxResult list(SysNotice notice) {
        if (UserContext.isCampusAdmin()) {
            notice.setSchoolId(UserContext.getSchoolId());
        }
        startPage();
        List<SysNotice> list = noticeService.selectNoticeList(notice);
        return getDataTable(list);
    }

    @GetMapping("/{noticeId}")
    public AjaxResult getInfo(@PathVariable Long noticeId) {
        return AjaxResult.success(noticeService.selectNoticeById(noticeId));
    }

    @RequiresRole("operation_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysNotice notice) {
        return toAjax(noticeService.insertNotice(notice));
    }

    @RequiresRole("operation_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysNotice notice) {
        return toAjax(noticeService.updateNotice(notice));
    }

    @RequiresRole("operation_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += noticeService.deleteNoticeById(id);
        }
        return toAjax(total);
    }
}
