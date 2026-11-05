package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 宿舍楼实体
 */
@Data
public class SysDormitory implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dormitoryId;
    private String dormitoryName;

    /** 学校 ID */
    private Long schoolId;

    private String buildingNo;
    private Integer floorCount;
    private String location;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
