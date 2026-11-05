package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 驿站实体
 */
@Data
public class SysExpressPoint implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long pointId;
    private String pointName;

    /** 学校 ID */
    private Long schoolId;

    private String location;
    private String managerName;
    private String phone;
    private String status;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
