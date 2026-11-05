package com.campus.pickup.system.service.impl;

import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysRole;
import com.campus.pickup.system.mapper.SysRoleMapper;
import com.campus.pickup.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * 角色服务实现
 */
@Service
public class SysRoleServiceImpl implements ISysRoleService {

    @Autowired
    private SysRoleMapper roleMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysRole> selectRoleList(SysRole role) {
        return roleMapper.selectRoleList(role);
    }

    @Override
    public SysRole selectRoleById(Long roleId) {
        return roleMapper.selectRoleById(roleId);
    }

    @Override
    public int insertRole(SysRole role) {
        int rows = roleMapper.insertRole(role);
        redisCache.deleteByPrefix(CacheConstants.ROLE);
        return rows;
    }

    @Override
    public int updateRole(SysRole role) {
        int rows = roleMapper.updateRole(role);
        redisCache.deleteByPrefix(CacheConstants.ROLE);
        return rows;
    }

    @Override
    public int deleteRoleById(Long roleId) {
        int rows = roleMapper.deleteRoleById(roleId);
        redisCache.deleteByPrefix(CacheConstants.ROLE);
        return rows;
    }

    @Override
    public Set<String> selectRoleKeysByUserId(Long userId) {
        List<String> keys = roleMapper.selectRoleKeysByUserId(userId);
        return keys == null ? new HashSet<>() : new HashSet<>(keys);
    }

    @Override
    public Long selectRoleIdByKey(String roleKey) {
        return roleMapper.selectRoleIdByKey(roleKey);
    }
}
