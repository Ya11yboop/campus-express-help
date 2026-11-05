package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户收货地址实体
 */
@Data
public class SysUserAddress implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long addressId;
    private Long userId;

    /** 联系人姓名 */
    private String contactName;

    /** 联系电话 */
    private String contactPhone;

    /** 详细地址 */
    private String address;

    /** 是否默认（0 否 1 是） */
    private String isDefault;

    private Date createTime;
    private Date updateTime;
}
