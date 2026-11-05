package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysExpressPoint;

import java.util.List;

/**
 * 驿站服务接口
 */
public interface ISysExpressPointService {

    List<SysExpressPoint> selectPointList(SysExpressPoint point);

    SysExpressPoint selectPointById(Long pointId);

    List<SysExpressPoint> selectPointAll(Long schoolId);

    int insertPoint(SysExpressPoint point);

    int updatePoint(SysExpressPoint point);

    int deletePointById(Long pointId);
}
