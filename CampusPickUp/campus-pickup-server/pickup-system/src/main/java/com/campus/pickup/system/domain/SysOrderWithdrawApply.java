package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 接单撤回申请实体
 */
@Data
public class SysOrderWithdrawApply implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long applyId;
    private Long orderId;

    /** 订单号（关联 sys_order，仅列表展示用） */
    private String orderNo;

    /** 接单人ID（申请人） */
    private Long courierId;

    /** 接单人用户名（关联 sys_user，仅列表展示用） */
    private String courierName;

    /** 撤回理由 */
    private String reason;

    /** 审核状态（0待审核 1通过 2驳回） */
    private String applyStatus;

    private Long auditUserId;
    private Date auditTime;

    /** 审核备注/驳回原因 */
    private String remark;

    private Date createTime;
}
