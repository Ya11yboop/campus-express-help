package com.campus.pickup.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysSchool;
import com.campus.pickup.system.mapper.SysSchoolMapper;
import com.campus.pickup.system.service.ISysSchoolService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 学校服务实现
 */
@Service
public class SysSchoolServiceImpl implements ISysSchoolService {

    private static final Logger log = LoggerFactory.getLogger(SysSchoolServiceImpl.class);

    @Autowired
    private SysSchoolMapper schoolMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysSchool> selectSchoolList(SysSchool school) {
        return schoolMapper.selectSchoolList(school);
    }

    @Override
    public SysSchool selectSchoolById(Long schoolId) {
        return schoolMapper.selectSchoolById(schoolId);
    }

    @Override
    public List<SysSchool> selectSchoolAll() {
        String key = CacheConstants.SCHOOL + "all";
        String cached = redisCache.get(key);
        if (cached != null) {
            return JSON.parseArray(cached, SysSchool.class);
        }
        List<SysSchool> list = schoolMapper.selectSchoolAll();
        redisCache.set(key, list);
        return list;
    }

    @Override
    public int insertSchool(SysSchool school) {
        int rows = schoolMapper.insertSchool(school);
        redisCache.deleteByPrefix(CacheConstants.SCHOOL);
        return rows;
    }

    @Override
    public int updateSchool(SysSchool school) {
        int rows = schoolMapper.updateSchool(school);
        redisCache.deleteByPrefix(CacheConstants.SCHOOL);
        return rows;
    }

    @Override
    public int deleteSchoolById(Long schoolId) {
        int userCount = schoolMapper.countUserBySchoolId(schoolId);
        if (userCount > 0) {
            log.warn("【删除学校】学校ID={} 下存在 {} 个用户，禁止删除", schoolId, userCount);
            throw new ServiceException("该学校下存在 " + userCount + " 个用户，请先删除或转移用户后再删除学校");
        }
        int rows = schoolMapper.deleteSchoolById(schoolId);
        redisCache.deleteByPrefix(CacheConstants.SCHOOL);
        return rows;
    }
}
