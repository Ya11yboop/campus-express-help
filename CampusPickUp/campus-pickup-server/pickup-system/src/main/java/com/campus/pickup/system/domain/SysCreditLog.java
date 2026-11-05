package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 信誉分变更记录实体
 */
@Data
public class SysCreditLog implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long logId;
    private Long userId;

    /** 学校 ID */
    private Long schoolId;

    private Integer changeScore;
    private Integer creditScore;
    private String reason;
    private Long operatorId;
    private Date createTime;
}
