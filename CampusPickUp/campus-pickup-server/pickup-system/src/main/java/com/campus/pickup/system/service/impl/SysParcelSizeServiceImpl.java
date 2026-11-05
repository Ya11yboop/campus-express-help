package com.campus.pickup.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysParcelSize;
import com.campus.pickup.system.mapper.SysParcelSizeMapper;
import com.campus.pickup.system.service.ISysParcelSizeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 快递大小分类服务实现
 */
@Service
public class SysParcelSizeServiceImpl implements ISysParcelSizeService {

    @Autowired
    private SysParcelSizeMapper sizeMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysParcelSize> selectSizeList(SysParcelSize size) {
        return sizeMapper.selectSizeList(size);
    }

    @Override
    public SysParcelSize selectSizeById(Long sizeId) {
        return sizeMapper.selectSizeById(sizeId);
    }

    @Override
    public List<SysParcelSize> selectSizeAll() {
        String key = CacheConstants.SIZE + "all";
        String cached = redisCache.get(key);
        if (cached != null) {
            return JSON.parseArray(cached, SysParcelSize.class);
        }
        List<SysParcelSize> list = sizeMapper.selectSizeAll();
        redisCache.set(key, list);
        return list;
    }

    @Override
    public int insertSize(SysParcelSize size) {
        int rows = sizeMapper.insertSize(size);
        redisCache.deleteByPrefix(CacheConstants.SIZE);
        return rows;
    }

    @Override
    public int updateSize(SysParcelSize size) {
        int rows = sizeMapper.updateSize(size);
        redisCache.deleteByPrefix(CacheConstants.SIZE);
        return rows;
    }

    @Override
    public int deleteSizeById(Long sizeId) {
        int rows = sizeMapper.deleteSizeById(sizeId);
        redisCache.deleteByPrefix(CacheConstants.SIZE);
        return rows;
    }
}
