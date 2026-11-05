package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysOrder;

import java.util.List;

/**
 * 订单服务接口
 */
public interface ISysOrderService {

    List<SysOrder> selectOrderList(SysOrder order);

    SysOrder selectOrderById(Long orderId);

    Long createOrder(SysOrder order);

    int updateOrder(SysOrder order);

    int acceptOrder(Long orderId, Long courierId);

    int finishOrder(Long orderId);

    int cancelOrder(Long orderId);

    int withdrawOrder(Long orderId);

    int deleteOrderById(Long orderId);
}
