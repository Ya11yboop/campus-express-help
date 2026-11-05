package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 信誉分扣除申请实体
 */
@Data
public class SysCreditApply implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long applyId;

    /** 被扣分用户ID */
    private Long userId;

    /** 被扣分用户名（关联 sys_user，仅列表展示用） */
    private String userName;

    /** 变更分数（负数=扣除） */
    private Integer changeScore;

    /** 申请原因 */
    private String reason;

    /** 审核状态（0待审核 1通过 2驳回） */
    private String applyStatus;

    /** 申请人ID（客服） */
    private Long applyUserId;

    /** 审核人ID */
    private Long auditUserId;

    /** 审核时间 */
    private Date auditTime;

    /** 申请时间 */
    private Date createTime;

    /** 审核备注/驳回原因 */
    private String remark;
}
