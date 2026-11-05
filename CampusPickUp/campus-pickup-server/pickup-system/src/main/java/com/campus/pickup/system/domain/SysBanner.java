package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 轮播图实体
 */
@Data
public class SysBanner implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long bannerId;
    private String title;
    private String image;
    private String linkType;
    private String linkUrl;
    private String status;
    private Integer sort;
    private Date createTime;
    private Date updateTime;
    private String remark;
}
