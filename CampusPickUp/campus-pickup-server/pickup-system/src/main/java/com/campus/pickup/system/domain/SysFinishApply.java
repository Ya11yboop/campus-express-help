package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 配送完成申请实体
 */
@Data
public class SysFinishApply implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long applyId;
    private Long orderId;

    /** 订单号（关联 sys_order，仅列表展示用） */
    private String orderNo;

    private Long courierId;

    /** 配送完成照片地址 */
    private String image;

    /** 审核状态（0待审核 1通过 2驳回） */
    private String applyStatus;

    /** 审核备注/驳回原因 */
    private String remark;

    private Long auditUserId;
    private Date auditTime;
    private Date createTime;
}
