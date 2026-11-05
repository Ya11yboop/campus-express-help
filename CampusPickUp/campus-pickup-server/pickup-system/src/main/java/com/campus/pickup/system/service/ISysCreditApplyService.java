package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysCreditApply;

import java.util.List;

/**
 * 信誉分扣除申请服务
 */
public interface ISysCreditApplyService {

    List<SysCreditApply> selectList(SysCreditApply apply);

    SysCreditApply selectById(Long applyId);

    int insert(SysCreditApply apply);

    int audit(Long applyId, boolean approve, Long auditUserId, String remark);
}
