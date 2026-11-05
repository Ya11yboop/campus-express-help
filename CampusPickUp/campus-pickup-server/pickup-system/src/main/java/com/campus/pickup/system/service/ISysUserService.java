package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysUser;

import java.util.List;

/**
 * 用户服务接口
 *
 * @author campus-pickup
 */
public interface ISysUserService {

    SysUser selectUserByUserName(String userName);

    SysUser selectUserById(Long userId);

    SysUser selectUserByOpenId(String openid);

    List<SysUser> selectUserList(SysUser user);

    int insertUser(SysUser user);

    int updateUser(SysUser user);

    int updateCreditScore(Long userId, Integer creditScore);

    int deleteUserById(Long userId);

    int insertUserRole(Long userId, Long roleId);
}
