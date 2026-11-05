package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 字典数据实体
 */
@Data
public class SysDictData implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long dictCode;
    private Integer dictSort;
    private String dictLabel;
    private String dictValue;
    private String dictType;
    private String status;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
