package com.campus.pickup.system.service.impl;

import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysUser;
import com.campus.pickup.system.mapper.SysUserMapper;
import com.campus.pickup.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户服务实现
 */
@Service
public class SysUserServiceImpl implements ISysUserService {

    @Autowired
    private SysUserMapper userMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public SysUser selectUserByUserName(String userName) {
        return userMapper.selectUserByUserName(userName);
    }

    @Override
    public SysUser selectUserById(Long userId) {
        return userMapper.selectUserById(userId);
    }

    @Override
    public SysUser selectUserByOpenId(String openid) {
        return userMapper.selectUserByOpenId(openid);
    }

    @Override
    public List<SysUser> selectUserList(SysUser user) {
        return userMapper.selectUserList(user);
    }

    @Override
    public int insertUser(SysUser user) {
        int rows = userMapper.insertUser(user);
        redisCache.deleteByPrefix(CacheConstants.USER);
        return rows;
    }

    @Override
    public int updateUser(SysUser user) {
        int rows = userMapper.updateUser(user);
        redisCache.deleteByPrefix(CacheConstants.USER);
        return rows;
    }

    @Override
    public int updateCreditScore(Long userId, Integer creditScore) {
        int rows = userMapper.updateCreditScore(userId, creditScore);
        redisCache.deleteByPrefix(CacheConstants.USER);
        return rows;
    }

    @Override
    public int deleteUserById(Long userId) {
        int rows = userMapper.deleteUserById(userId);
        redisCache.deleteByPrefix(CacheConstants.USER);
        return rows;
    }

    @Override
    public int insertUserRole(Long userId, Long roleId) {
        return userMapper.insertUserRole(userId, roleId);
    }
}
