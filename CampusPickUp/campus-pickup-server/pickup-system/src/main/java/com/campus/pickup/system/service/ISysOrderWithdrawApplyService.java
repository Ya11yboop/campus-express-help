package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysOrderWithdrawApply;

import java.util.List;

/**
 * 接单撤回申请服务
 */
public interface ISysOrderWithdrawApplyService {

    List<SysOrderWithdrawApply> selectList(SysOrderWithdrawApply apply);

    SysOrderWithdrawApply selectById(Long applyId);

    int insert(SysOrderWithdrawApply apply);

    int audit(Long applyId, boolean approve, String remark, Long auditUserId);
}
