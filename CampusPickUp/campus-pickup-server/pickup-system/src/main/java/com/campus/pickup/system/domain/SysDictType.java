package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典类型实体
 */
@Data
public class SysDictType implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dictId;
    private String dictName;
    private String dictType;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
