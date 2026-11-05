package com.campus.pickup.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysExpressPoint;
import com.campus.pickup.system.mapper.SysExpressPointMapper;
import com.campus.pickup.system.service.ISysExpressPointService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 驿站服务实现
 */
@Service
public class SysExpressPointServiceImpl implements ISysExpressPointService {

    @Autowired
    private SysExpressPointMapper pointMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysExpressPoint> selectPointList(SysExpressPoint point) {
        return pointMapper.selectPointList(point);
    }

    @Override
    public SysExpressPoint selectPointById(Long pointId) {
        return pointMapper.selectPointById(pointId);
    }

    @Override
    public List<SysExpressPoint> selectPointAll(Long schoolId) {
        String key = CacheConstants.POINT + "all:" + (schoolId == null ? "all" : schoolId);
        String cached = redisCache.get(key);
        if (cached != null) {
            return JSON.parseArray(cached, SysExpressPoint.class);
        }
        List<SysExpressPoint> list = pointMapper.selectPointAll(schoolId);
        redisCache.set(key, list);
        return list;
    }

    @Override
    public int insertPoint(SysExpressPoint point) {
        int rows = pointMapper.insertPoint(point);
        redisCache.deleteByPrefix(CacheConstants.POINT);
        return rows;
    }

    @Override
    public int updatePoint(SysExpressPoint point) {
        int rows = pointMapper.updatePoint(point);
        redisCache.deleteByPrefix(CacheConstants.POINT);
        return rows;
    }

    @Override
    public int deletePointById(Long pointId) {
        int rows = pointMapper.deletePointById(pointId);
        redisCache.deleteByPrefix(CacheConstants.POINT);
        return rows;
    }
}
