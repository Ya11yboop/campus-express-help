package com.campus.pickup.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysNotice;
import com.campus.pickup.system.mapper.SysNoticeMapper;
import com.campus.pickup.system.service.ISysNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 公告服务实现
 */
@Service
public class SysNoticeServiceImpl implements ISysNoticeService {

    @Autowired
    private SysNoticeMapper noticeMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysNotice> selectNoticeList(SysNotice notice) {
        return noticeMapper.selectNoticeList(notice);
    }

    @Override
    public SysNotice selectNoticeById(Long noticeId) {
        return noticeMapper.selectNoticeById(noticeId);
    }

    @Override
    public List<SysNotice> selectNoticeAll(Long schoolId) {
        String key = CacheConstants.NOTICE + "all:" + (schoolId == null ? "all" : schoolId);
        String cached = redisCache.get(key);
        if (cached != null) {
            return JSON.parseArray(cached, SysNotice.class);
        }
        List<SysNotice> list = noticeMapper.selectNoticeAll(schoolId);
        redisCache.set(key, list);
        return list;
    }

    @Override
    public int insertNotice(SysNotice notice) {
        int rows = noticeMapper.insertNotice(notice);
        redisCache.deleteByPrefix(CacheConstants.NOTICE);
        return rows;
    }

    @Override
    public int updateNotice(SysNotice notice) {
        int rows = noticeMapper.updateNotice(notice);
        redisCache.deleteByPrefix(CacheConstants.NOTICE);
        return rows;
    }

    @Override
    public int deleteNoticeById(Long noticeId) {
        int rows = noticeMapper.deleteNoticeById(noticeId);
        redisCache.deleteByPrefix(CacheConstants.NOTICE);
        return rows;
    }
}
