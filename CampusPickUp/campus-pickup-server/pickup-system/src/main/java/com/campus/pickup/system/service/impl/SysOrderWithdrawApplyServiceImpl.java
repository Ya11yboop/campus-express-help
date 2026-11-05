package com.campus.pickup.system.service.impl;

import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.system.domain.SysOrderWithdrawApply;
import com.campus.pickup.system.mapper.SysOrderWithdrawApplyMapper;
import com.campus.pickup.system.service.ISysOrderService;
import com.campus.pickup.system.service.ISysOrderWithdrawApplyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 接单撤回申请服务实现
 */
@Service
public class SysOrderWithdrawApplyServiceImpl implements ISysOrderWithdrawApplyService {

    @Autowired
    private SysOrderWithdrawApplyMapper applyMapper;

    @Autowired
    private ISysOrderService orderService;

    @Override
    public List<SysOrderWithdrawApply> selectList(SysOrderWithdrawApply apply) {
        return applyMapper.selectList(apply);
    }

    @Override
    public SysOrderWithdrawApply selectById(Long applyId) {
        return applyMapper.selectById(applyId);
    }

    @Override
    public int insert(SysOrderWithdrawApply apply) {
        return applyMapper.insert(apply);
    }

    @Override
    @Transactional
    public int audit(Long applyId, boolean approve, String remark, Long auditUserId) {
        SysOrderWithdrawApply apply = applyMapper.selectById(applyId);
        if (apply == null) {
            throw new ServiceException("申请不存在");
        }
        if (!"0".equals(apply.getApplyStatus())) {
            throw new ServiceException("该申请已审核，请勿重复操作");
        }
        String status = approve ? "1" : "2";
        if (approve) {
            // 通过后订单恢复为待接单
            orderService.withdrawOrder(apply.getOrderId());
        }
        return applyMapper.audit(applyId, status, auditUserId, remark);
    }
}
