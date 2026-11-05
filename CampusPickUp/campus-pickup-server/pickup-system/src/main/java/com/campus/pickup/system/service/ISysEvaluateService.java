package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysEvaluate;

import java.util.List;

/**
 * 评价服务接口
 */
public interface ISysEvaluateService {

    List<SysEvaluate> selectEvaluateList(SysEvaluate evaluate);

    SysEvaluate selectEvaluateById(Long evaluateId);

    int insertEvaluate(SysEvaluate evaluate);

    int replyEvaluate(SysEvaluate evaluate);

    int deleteEvaluateById(Long evaluateId);
}
