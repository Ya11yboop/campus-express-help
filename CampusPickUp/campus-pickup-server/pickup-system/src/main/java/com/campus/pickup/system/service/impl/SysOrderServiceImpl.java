package com.campus.pickup.system.service.impl;

import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysOrder;
import com.campus.pickup.system.domain.SysUser;
import com.campus.pickup.system.mapper.SysOrderMapper;
import com.campus.pickup.system.service.ISysOrderService;
import com.campus.pickup.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Random;

/**
 * 订单服务实现
 */
@Service
public class SysOrderServiceImpl implements ISysOrderService {

    @Autowired
    private SysOrderMapper orderMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Autowired
    private ISysUserService userService;

    @Override
    public List<SysOrder> selectOrderList(SysOrder order) {
        return orderMapper.selectOrderList(order);
    }

    @Override
    public SysOrder selectOrderById(Long orderId) {
        return orderMapper.selectOrderById(orderId);
    }

    @Override
    public Long createOrder(SysOrder order) {
        order.setOrderNo(generateOrderNo());
        if (order.getSchoolId() == null && order.getUserId() != null) {
            SysUser user = userService.selectUserById(order.getUserId());
            if (user != null) {
                order.setSchoolId(user.getSchoolId());
            }
        }
        if (order.getOrderStatus() == null) {
            order.setOrderStatus("0");
        }
        if (order.getFee() == null) {
            order.setFee(BigDecimal.ZERO);
        }
        order.setCreateTime(new Date());
        orderMapper.insertOrder(order);
        redisCache.deleteByPrefix(CacheConstants.ORDER);
        return order.getOrderId();
    }

    @Override
    public int updateOrder(SysOrder order) {
        int rows = orderMapper.updateOrder(order);
        redisCache.deleteByPrefix(CacheConstants.ORDER);
        return rows;
    }

    @Override
    public int acceptOrder(Long orderId, Long courierId) {
        int rows = orderMapper.acceptOrder(orderId, courierId);
        redisCache.deleteByPrefix(CacheConstants.ORDER);
        return rows;
    }

    @Override
    public int finishOrder(Long orderId) {
        int rows = orderMapper.finishOrder(orderId);
        redisCache.deleteByPrefix(CacheConstants.ORDER);
        return rows;
    }

    @Override
    public int cancelOrder(Long orderId) {
        int rows = orderMapper.cancelOrder(orderId);
        redisCache.deleteByPrefix(CacheConstants.ORDER);
        return rows;
    }

    @Override
    public int withdrawOrder(Long orderId) {
        int rows = orderMapper.withdrawOrder(orderId);
        redisCache.deleteByPrefix(CacheConstants.ORDER);
        return rows;
    }

    @Override
    public int deleteOrderById(Long orderId) {
        int rows = orderMapper.deleteOrderById(orderId);
        redisCache.deleteByPrefix(CacheConstants.ORDER);
        return rows;
    }

    private String generateOrderNo() {
        return "CP" + System.currentTimeMillis() + String.format("%04d", new Random().nextInt(10000));
    }
}
