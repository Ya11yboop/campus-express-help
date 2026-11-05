package com.campus.pickup.system.domain;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 用户实体
 *
 * @author campus-pickup
 */
@Data
public class SysUser implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 用户 ID */
    private Long userId;

    /** 微信 openid（唯一标识，微信登录关联用） */
    private String openid;

    /** 用户名（登录账号） */
    private String userName;

    /** 昵称 */
    private String nickName;

    /** 真实姓名 */
    private String realName;

    /** 密码（MD5 加密） */
    private String password;

    /** 学校（文本，兼容旧数据） */
    private String school;

    /** 学校 ID（关联 sys_school） */
    private Long schoolId;

    /** 学号 */
    private String studentNo;

    /** 身份证号 */
    private String idCard;

    /** 手机号 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 头像地址 */
    private String avatar;

    /** 性别（0 男 1 女 2 未知） */
    private String gender;

    /** 宿舍楼 ID */
    private Long dormitoryId;

    /** 宿舍房间号 */
    private String roomNo;

    /** 信誉分 */
    private Integer creditScore;

    /** 账号状态（0 正常 1 停用） */
    private String status;

    /** 用户类型（admin/courier/user） */
    private String userType;

    /** 创建时间 */
    private Date createTime;

    /** 更新时间 */
    private Date updateTime;

    /** 备注 */
    private String remark;
}
