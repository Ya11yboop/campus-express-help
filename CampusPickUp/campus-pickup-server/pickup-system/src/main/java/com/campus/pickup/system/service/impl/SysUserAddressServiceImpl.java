package com.campus.pickup.system.service.impl;

import com.campus.pickup.system.domain.SysUserAddress;
import com.campus.pickup.system.mapper.SysUserAddressMapper;
import com.campus.pickup.system.service.ISysUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户收货地址服务实现
 */
@Service
public class SysUserAddressServiceImpl implements ISysUserAddressService {

    @Autowired
    private SysUserAddressMapper addressMapper;

    @Override
    public List<SysUserAddress> selectByUserId(Long userId) {
        return addressMapper.selectByUserId(userId);
    }

    @Override
    public SysUserAddress selectById(Long addressId) {
        return addressMapper.selectById(addressId);
    }

    @Override
    public int insert(SysUserAddress address) {
        if (address.getIsDefault() == null) {
            address.setIsDefault("0");
        }
        if ("1".equals(address.getIsDefault())) {
            addressMapper.clearDefaultByUserId(address.getUserId());
        }
        return addressMapper.insert(address);
    }

    @Override
    public int update(SysUserAddress address) {
        if ("1".equals(address.getIsDefault())) {
            addressMapper.clearDefaultByUserId(address.getUserId());
        }
        return addressMapper.update(address);
    }

    @Override
    public int deleteById(Long addressId) {
        return addressMapper.deleteById(addressId);
    }

    @Override
    public int setDefault(Long userId, Long addressId) {
        addressMapper.clearDefaultByUserId(userId);
        return addressMapper.setDefaultById(addressId);
    }
}
