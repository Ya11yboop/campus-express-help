package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 申诉/投诉实体
 */
@Data
public class SysComplaint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long complaintId;
    private Long orderId;

    /** 订单号（关联 sys_order，仅列表展示用） */
    private String orderNo;

    /** 学校 ID */
    private Long schoolId;

    private Long userId;

    /** 下单用户名（关联 sys_user，仅列表展示用） */
    private String userName;

    /** 被投诉/申诉的用户ID（接单的人） */
    private Long targetUserId;

    /** 被投诉/申诉的用户名（接单的人，仅列表展示用） */
    private String targetUserName;

    private String complaintType;
    private String content;
    private String images;
    private String handleStatus;
    private String handleResult;
    private Long handlerId;
    private Date handleTime;
    private Date createTime;
    private String remark;
}
