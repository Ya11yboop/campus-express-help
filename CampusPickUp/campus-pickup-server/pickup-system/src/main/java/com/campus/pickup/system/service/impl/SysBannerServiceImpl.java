package com.campus.pickup.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysBanner;
import com.campus.pickup.system.mapper.SysBannerMapper;
import com.campus.pickup.system.service.ISysBannerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 轮播图服务实现
 */
@Service
public class SysBannerServiceImpl implements ISysBannerService {

    @Autowired
    private SysBannerMapper bannerMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysBanner> selectBannerList(SysBanner banner) {
        return bannerMapper.selectBannerList(banner);
    }

    @Override
    public SysBanner selectBannerById(Long bannerId) {
        return bannerMapper.selectBannerById(bannerId);
    }

    @Override
    public List<SysBanner> selectBannerAll() {
        String key = CacheConstants.BANNER + "all";
        String cached = redisCache.get(key);
        if (cached != null) {
            return JSON.parseArray(cached, SysBanner.class);
        }
        List<SysBanner> list = bannerMapper.selectBannerAll();
        redisCache.set(key, list);
        return list;
    }

    @Override
    public int insertBanner(SysBanner banner) {
        int rows = bannerMapper.insertBanner(banner);
        redisCache.deleteByPrefix(CacheConstants.BANNER);
        return rows;
    }

    @Override
    public int updateBanner(SysBanner banner) {
        int rows = bannerMapper.updateBanner(banner);
        redisCache.deleteByPrefix(CacheConstants.BANNER);
        return rows;
    }

    @Override
    public int deleteBannerById(Long bannerId) {
        int rows = bannerMapper.deleteBannerById(bannerId);
        redisCache.deleteByPrefix(CacheConstants.BANNER);
        return rows;
    }
}
