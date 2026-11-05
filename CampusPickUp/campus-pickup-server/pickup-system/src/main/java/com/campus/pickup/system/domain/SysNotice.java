package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 公告实体
 */
@Data
public class SysNotice implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long noticeId;
    private String title;
    private String content;
    private String noticeType;

    /** 学校 ID */
    private Long schoolId;

    private String status;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
