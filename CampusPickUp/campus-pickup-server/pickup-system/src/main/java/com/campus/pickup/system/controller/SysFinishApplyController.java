package com.campus.pickup.system.controller;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.framework.websocket.NotifyWebSocketHandler;
import com.campus.pickup.system.domain.SysFinishApply;
import com.campus.pickup.system.domain.SysNotification;
import com.campus.pickup.system.domain.SysOrder;
import com.campus.pickup.system.mapper.SysNotificationMapper;
import com.campus.pickup.system.service.ISysFinishApplyService;
import com.campus.pickup.system.service.ISysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 配送完成申请控制器
 */
@RestController
@RequestMapping("/finish/apply")
public class SysFinishApplyController extends BaseController {

    @Autowired
    private ISysFinishApplyService applyService;

    @Autowired
    private ISysOrderService orderService;

    @Autowired
    private NotifyWebSocketHandler notifyHandler;

    @Autowired
    private SysNotificationMapper notificationMapper;

    /** 接单人申请配送完成（上传照片） */
    @PostMapping
    public AjaxResult add(@RequestBody SysFinishApply apply) {
        if (apply.getOrderId() == null) {
            return AjaxResult.error("订单不能为空");
        }
        SysOrder order = orderService.selectOrderById(apply.getOrderId());
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        if (order.getCourierId() == null || !order.getCourierId().equals(UserContext.getUserId())) {
            return AjaxResult.error("只能由接单人申请配送完成");
        }
        apply.setApplyId(null);
        apply.setCourierId(UserContext.getUserId());
        apply.setApplyStatus("0");
        int rows = applyService.insert(apply);

        // WebSocket 通知管理端
        Map<String, Object> notify = new HashMap<>();
        notify.put("type", "finishApply");
        notify.put("msg", "有新的配送完成申请，订单号：" + order.getOrderNo());
        notifyHandler.broadcast(JSON.toJSONString(notify));

        // 通知下单用户
        if (order.getUserId() != null) {
            SysNotification notification = new SysNotification();
            notification.setUserId(order.getUserId());
            notification.setTitle("配送完成申请");
            notification.setContent("你的订单 " + order.getOrderNo() + " 的配送员已申请配送完成，请留意。");
            notificationMapper.insert(notification);
        }
        return toAjax(rows);
    }

    /** 申请列表 */
    @RequiresRole({"super_admin", "operation_admin", "cs_admin"})
    @GetMapping("/list")
    public AjaxResult list(SysFinishApply apply) {
        startPage();
        List<SysFinishApply> list = applyService.selectList(apply);
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
