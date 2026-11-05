package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * 快递大小分类实体
 */
@Data
public class SysParcelSize implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long sizeId;
    private String sizeName;
    private String sizeDesc;
    private BigDecimal fee;
    private String status;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
