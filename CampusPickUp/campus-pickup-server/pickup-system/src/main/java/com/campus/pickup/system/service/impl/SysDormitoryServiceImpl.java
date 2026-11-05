package com.campus.pickup.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysDormitory;
import com.campus.pickup.system.mapper.SysDormitoryMapper;
import com.campus.pickup.system.service.ISysDormitoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 宿舍楼服务实现
 */
@Service
public class SysDormitoryServiceImpl implements ISysDormitoryService {

    @Autowired
    private SysDormitoryMapper dormitoryMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysDormitory> selectDormitoryList(SysDormitory dormitory) {
        return dormitoryMapper.selectDormitoryList(dormitory);
    }

    @Override
    public SysDormitory selectDormitoryById(Long dormitoryId) {
        return dormitoryMapper.selectDormitoryById(dormitoryId);
    }

    @Override
    public List<SysDormitory> selectDormitoryAll(Long schoolId) {
        String key = CacheConstants.DORMITORY + "all:" + (schoolId == null ? "all" : schoolId);
        String cached = redisCache.get(key);
        if (cached != null) {
            return JSON.parseArray(cached, SysDormitory.class);
        }
        List<SysDormitory> list = dormitoryMapper.selectDormitoryAll(schoolId);
        redisCache.set(key, list);
        return list;
    }

    @Override
    public int insertDormitory(SysDormitory dormitory) {
        int rows = dormitoryMapper.insertDormitory(dormitory);
        redisCache.deleteByPrefix(CacheConstants.DORMITORY);
        return rows;
    }

    @Override
    public int updateDormitory(SysDormitory dormitory) {
        int rows = dormitoryMapper.updateDormitory(dormitory);
        redisCache.deleteByPrefix(CacheConstants.DORMITORY);
        return rows;
    }

    @Override
    public int deleteDormitoryById(Long dormitoryId) {
        int rows = dormitoryMapper.deleteDormitoryById(dormitoryId);
        redisCache.deleteByPrefix(CacheConstants.DORMITORY);
        return rows;
    }
}
