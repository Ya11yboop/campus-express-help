package com.campus.pickup.system.service.impl;

import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysEvaluate;
import com.campus.pickup.system.mapper.SysEvaluateMapper;
import com.campus.pickup.system.service.ISysEvaluateService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 评价服务实现
 */
@Service
public class SysEvaluateServiceImpl implements ISysEvaluateService {

    @Autowired
    private SysEvaluateMapper evaluateMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysEvaluate> selectEvaluateList(SysEvaluate evaluate) {
        return evaluateMapper.selectEvaluateList(evaluate);
    }

    @Override
    public SysEvaluate selectEvaluateById(Long evaluateId) {
        return evaluateMapper.selectEvaluateById(evaluateId);
    }

    @Override
    public int insertEvaluate(SysEvaluate evaluate) {
        int rows = evaluateMapper.insertEvaluate(evaluate);
        redisCache.deleteByPrefix(CacheConstants.EVALUATE);
        return rows;
    }

    @Override
    public int replyEvaluate(SysEvaluate evaluate) {
        int rows = evaluateMapper.replyEvaluate(evaluate);
        redisCache.deleteByPrefix(CacheConstants.EVALUATE);
        return rows;
    }

    @Override
    public int deleteEvaluateById(Long evaluateId) {
        int rows = evaluateMapper.deleteEvaluateById(evaluateId);
        redisCache.deleteByPrefix(CacheConstants.EVALUATE);
        return rows;
    }
}
