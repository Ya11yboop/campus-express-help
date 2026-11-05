package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户通知实体
 */
@Data
public class SysNotification implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long notifId;
    private Long userId;
    private String title;
    private String content;
    private String readStatus;
    private Date createTime;
}
