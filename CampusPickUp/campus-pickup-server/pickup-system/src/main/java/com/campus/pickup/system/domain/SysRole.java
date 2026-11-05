package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 角色实体
 *
 * @author campus-pickup
 */
@Data
public class SysRole implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 角色 ID */
    private Long roleId;

    /** 角色名称 */
    private String roleName;

    /** 角色权限字符串 */
    private String roleKey;

    /** 显示顺序 */
    private Integer roleSort;

    /** 状态（0 正常 1 停用） */
    private String status;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 备注 */
    private String remark;
}
