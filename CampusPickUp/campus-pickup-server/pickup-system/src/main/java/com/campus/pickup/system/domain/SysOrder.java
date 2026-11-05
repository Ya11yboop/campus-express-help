package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 订单实体（帮取/帮寄）
 */
@Data
public class SysOrder implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long orderId;
    private String orderNo;
    private Long userId;
    private Long courierId;

    /** 下单用户名（关联 sys_user，仅展示用） */
    private String userName;

    /** 接单人用户名（关联 sys_user，仅展示用） */
    private String courierName;

    private String orderType;
    private String trackingNo;
    private String expressCompany;
    private Long sizeId;
    private Long pointId;

    /** 学校 ID */
    private Long schoolId;

    private String pickupAddress;
    private String deliveryAddress;
    private String receiverName;
    private String receiverPhone;
    private BigDecimal fee;

    /** 费用下限（搜索用） */
    private BigDecimal feeMin;

    /** 费用上限（搜索用） */
    private BigDecimal feeMax;

    private String orderStatus;
    private Date createTime;
    private Date acceptTime;
    private Date finishTime;
    private String remark;
}
