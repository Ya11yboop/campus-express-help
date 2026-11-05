package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 评价实体
 */
@Data
public class SysEvaluate implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long evaluateId;
    private Long orderId;

    /** 学校 ID */
    private Long schoolId;

    private Long userId;
    private Long courierId;
    private Integer score;
    private String content;
    private String reply;
    private Date replyTime;
    private Date createTime;
}
