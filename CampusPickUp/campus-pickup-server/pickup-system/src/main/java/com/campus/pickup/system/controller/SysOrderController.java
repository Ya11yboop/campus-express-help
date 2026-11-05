package com.campus.pickup.system.controller;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.framework.websocket.NotifyWebSocketHandler;
import com.campus.pickup.system.domain.SysOrder;
import com.campus.pickup.system.domain.SysOrderWithdrawApply;
import com.campus.pickup.system.service.ISysOrderService;
import com.campus.pickup.system.service.ISysOrderWithdrawApplyService;
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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 订单控制器
 */
@RestController
@RequestMapping("/order")
public class SysOrderController extends BaseController {

    @Autowired
    private ISysOrderService orderService;

    @Autowired
    private ISysOrderWithdrawApplyService withdrawApplyService;

    @Autowired
    private NotifyWebSocketHandler notifyHandler;

    @Autowired
    private RedisCacheUtil redisCache;

    /** 下单（所有登录用户） */
    @PostMapping
    public AjaxResult create(@RequestBody SysOrder order) {
        order.setUserId(UserContext.getUserId());
        orderService.createOrder(order);
        return AjaxResult.success("下单成功");
    }

    /** 全量订单列表（运营/客服管理员） */
    @RequiresRole({"operation_admin", "cs_admin"})
    @GetMapping("/list")
    public AjaxResult list(SysOrder order) {
        if (UserContext.isCampusAdmin()) {
            order.setSchoolId(UserContext.getSchoolId());
        }
        startPage();
        List<SysOrder> list = orderService.selectOrderList(order);
        return getDataTable(list);
    }

    /** 我的订单（当前登录用户下的单） */
    @GetMapping("/my")
    public AjaxResult my() {
        String key = CacheConstants.ORDER + "my:" + UserContext.getUserId();
        String cached = redisCache.get(key);
        if (cached != null) {
            return AjaxResult.success(JSON.parseArray(cached, SysOrder.class));
        }
        SysOrder order = new SysOrder();
        order.setUserId(UserContext.getUserId());
        List<SysOrder> list = orderService.selectOrderList(order);
        redisCache.set(key, list);
        return AjaxResult.success(list);
    }

    /** 接单大厅（待接单，仅显示当前登录用户所属学校的订单，支持按费用/驿站/收货地址搜索；排除自己下的单） */
    @GetMapping("/hall")
    public AjaxResult hall(SysOrder order) {
        order.setOrderStatus("0");
        order.setSchoolId(UserContext.getSchoolId());
        List<SysOrder> list = orderService.selectOrderList(order);
        Long currentUserId = UserContext.getUserId();
        list.removeIf(o -> o.getUserId() != null && o.getUserId().equals(currentUserId));
        return AjaxResult.success(list);
    }

    /** 我接的单 */
    @GetMapping("/accepted")
    public AjaxResult accepted() {
        String key = CacheConstants.ORDER + "accepted:" + UserContext.getUserId();
        String cached = redisCache.get(key);
        if (cached != null) {
            return AjaxResult.success(JSON.parseArray(cached, SysOrder.class));
        }
        SysOrder order = new SysOrder();
        order.setCourierId(UserContext.getUserId());
        List<SysOrder> list = orderService.selectOrderList(order);
        redisCache.set(key, list);
        return AjaxResult.success(list);
    }

    @GetMapping("/{orderId}")
    public AjaxResult getInfo(@PathVariable Long orderId) {
        return AjaxResult.success(orderService.selectOrderById(orderId));
    }

    /** 接单 */
    @PutMapping("/accept/{orderId}")
    public AjaxResult accept(@PathVariable Long orderId) {
        SysOrder order = orderService.selectOrderById(orderId);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        if (order.getUserId() != null && order.getUserId().equals(UserContext.getUserId())) {
            return AjaxResult.error("不能接自己下的单");
        }
        Long schoolId = UserContext.getSchoolId();
        if (schoolId != null && order.getSchoolId() != null && !schoolId.equals(order.getSchoolId())) {
            return AjaxResult.error("只能接本学院的订单");
        }
        return toAjax(orderService.acceptOrder(orderId, UserContext.getUserId()));
    }

    /** 完成 */
    @PutMapping("/finish/{orderId}")
    public AjaxResult finish(@PathVariable Long orderId) {
        return toAjax(orderService.finishOrder(orderId));
    }

    /** 取消（仅下单人可取消自己的待接单订单） */
    @PutMapping("/cancel/{orderId}")
    public AjaxResult cancel(@PathVariable Long orderId) {
        SysOrder order = orderService.selectOrderById(orderId);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        if (order.getUserId() == null || !order.getUserId().equals(UserContext.getUserId())) {
            return AjaxResult.error("只能取消自己下的单");
        }
        if (!"0".equals(order.getOrderStatus())) {
            return AjaxResult.error("订单已被接单，无法取消");
        }
        return toAjax(orderService.cancelOrder(orderId));
    }

    /** 撤回接单（30分钟内无条件撤回，超过30分钟需填写理由申请） */
    @PutMapping("/withdraw/{orderId}")
    public AjaxResult withdraw(@PathVariable Long orderId, @RequestBody(required = false) Map<String, Object> body) {
        Long currentUserId = UserContext.getUserId();
        SysOrder order = orderService.selectOrderById(orderId);
        if (order == null) {
            return AjaxResult.error("订单不存在");
        }
        if (order.getCourierId() == null || !order.getCourierId().equals(currentUserId)) {
            return AjaxResult.error("只有接单人才能撤回接单");
        }
        if (!"1".equals(order.getOrderStatus())) {
            return AjaxResult.error("当前订单状态不可撤回");
        }
        String reason = null;
        if (body != null && body.get("reason") != null) {
            reason = String.valueOf(body.get("reason")).trim();
        }
        Date acceptTime = order.getAcceptTime();
        long minutes = acceptTime == null ? 0 : (System.currentTimeMillis() - acceptTime.getTime()) / 60000;
        if (acceptTime != null && minutes <= 30) {
            return toAjax(orderService.withdrawOrder(orderId));
        }
        if (reason == null || reason.isEmpty()) {
            return AjaxResult.error("接单已超过30分钟，撤回需填写申请理由");
        }
        SysOrderWithdrawApply apply = new SysOrderWithdrawApply();
        apply.setOrderId(orderId);
        apply.setCourierId(currentUserId);
        apply.setReason(reason);
        apply.setApplyStatus("0");
        int rows = withdrawApplyService.insert(apply);

        Map<String, Object> notify = new HashMap<>();
        notify.put("type", "withdrawApply");
        notify.put("msg", "有新的接单撤回申请，订单号：" + order.getOrderNo());
        notifyHandler.broadcast(JSON.toJSONString(notify));
        return toAjax(rows);
    }

    @RequiresRole("operation_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysOrder order) {
        return toAjax(orderService.updateOrder(order));
    }

    @RequiresRole("operation_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += orderService.deleteOrderById(id);
        }
        return toAjax(total);
    }
}
