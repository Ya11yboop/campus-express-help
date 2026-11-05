package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 学校实体
 */
@Data
public class SysSchool implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long schoolId;
    private String schoolName;
    private String schoolCode;
    private String address;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
