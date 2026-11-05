package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysCreditLog;

import java.util.List;

/**
 * 信誉分服务接口
 */
public interface ISysCreditService {

    List<SysCreditLog> selectLogList(SysCreditLog log);

    void changeScore(Long userId, Integer changeScore, String reason);
}
