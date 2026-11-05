package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysFinishApply;

import java.util.List;

/**
 * 配送完成申请服务
 */
public interface ISysFinishApplyService {

    List<SysFinishApply> selectList(SysFinishApply apply);

    SysFinishApply selectById(Long applyId);

    int insert(SysFinishApply apply);

    int audit(Long applyId, boolean approve, String remark, Long auditUserId);
}
