package com.campus.pickup.system.service.impl;

import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysComplaint;
import com.campus.pickup.system.mapper.SysComplaintMapper;
import com.campus.pickup.system.service.ISysComplaintService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 申诉/投诉服务实现
 */
@Service
public class SysComplaintServiceImpl implements ISysComplaintService {

    @Autowired
    private SysComplaintMapper complaintMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysComplaint> selectComplaintList(SysComplaint complaint) {
        return complaintMapper.selectComplaintList(complaint);
    }

    @Override
    public SysComplaint selectComplaintById(Long complaintId) {
        return complaintMapper.selectComplaintById(complaintId);
    }

    @Override
    public int insertComplaint(SysComplaint complaint) {
        int rows = complaintMapper.insertComplaint(complaint);
        redisCache.deleteByPrefix(CacheConstants.COMPLAINT);
        return rows;
    }

    @Override
    public int handleComplaint(SysComplaint complaint) {
        int rows = complaintMapper.handleComplaint(complaint);
        redisCache.deleteByPrefix(CacheConstants.COMPLAINT);
        return rows;
    }

    @Override
    public int deleteComplaintById(Long complaintId) {
        int rows = complaintMapper.deleteComplaintById(complaintId);
        redisCache.deleteByPrefix(CacheConstants.COMPLAINT);
        return rows;
    }
}
