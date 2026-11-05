package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.mapper.SysNotificationMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户通知控制器（小程序用）
 */
@RestController
@RequestMapping("/notification")
public class SysNotificationController {

    @Autowired
    private SysNotificationMapper notificationMapper;

    /** 我的通知 */
    @GetMapping("/my")
    public AjaxResult my() {
        return AjaxResult.success(notificationMapper.selectByUserId(UserContext.getUserId()));
    }

    /** 未读数量 */
    @GetMapping("/unreadCount")
    public AjaxResult unreadCount() {
        return AjaxResult.success(notificationMapper.countUnread(UserContext.getUserId()));
    }

    /** 标记已读 */
    @PutMapping("/read/{notifId}")
    public AjaxResult read(@PathVariable Long notifId) {
        notificationMapper.markRead(notifId, UserContext.getUserId());
        return AjaxResult.success("已读");
    }
}
