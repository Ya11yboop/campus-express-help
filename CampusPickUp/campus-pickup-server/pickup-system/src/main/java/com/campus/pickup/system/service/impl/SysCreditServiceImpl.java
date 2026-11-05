package com.campus.pickup.system.service.impl;

import com.campus.pickup.common.enums.ResultCode;
import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysCreditLog;
import com.campus.pickup.system.domain.SysUser;
import com.campus.pickup.system.mapper.SysCreditLogMapper;
import com.campus.pickup.system.service.ISysCreditService;
import com.campus.pickup.system.service.ISysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

/**
 * 信誉分服务实现
 */
@Service
public class SysCreditServiceImpl implements ISysCreditService {

    @Autowired
    private SysCreditLogMapper logMapper;

    @Autowired
    private ISysUserService userService;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysCreditLog> selectLogList(SysCreditLog log) {
        return logMapper.selectLogList(log);
    }

    @Override
    public void changeScore(Long userId, Integer changeScore, String reason) {
        SysUser user = userService.selectUserById(userId);
        if (user == null) {
            throw new ServiceException(ResultCode.USER_NOT_EXIST);
        }
        int current = user.getCreditScore() == null ? 100 : user.getCreditScore();
        int newScore = current + changeScore;
        if (newScore < 0) {
            newScore = 0;
        }
        userService.updateCreditScore(userId, newScore);

        SysCreditLog log = new SysCreditLog();
        log.setUserId(userId);
        log.setSchoolId(user.getSchoolId());
        log.setChangeScore(changeScore);
        log.setCreditScore(newScore);
        log.setReason(reason);
        log.setOperatorId(UserContext.getUserId());
        log.setCreateTime(new Date());
        logMapper.insertLog(log);
        redisCache.deleteByPrefix(CacheConstants.CREDIT);
    }
}
