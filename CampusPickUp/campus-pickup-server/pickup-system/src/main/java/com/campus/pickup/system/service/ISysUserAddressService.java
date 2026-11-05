package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysUserAddress;

import java.util.List;

/**
 * 用户收货地址服务
 */
public interface ISysUserAddressService {

    List<SysUserAddress> selectByUserId(Long userId);

    SysUserAddress selectById(Long addressId);

    int insert(SysUserAddress address);

    int update(SysUserAddress address);

    int deleteById(Long addressId);

    int setDefault(Long userId, Long addressId);
}
