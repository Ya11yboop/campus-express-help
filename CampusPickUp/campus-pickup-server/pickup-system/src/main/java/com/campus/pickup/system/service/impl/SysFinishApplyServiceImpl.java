package com.campus.pickup.system.service.impl;

import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.system.domain.SysFinishApply;
import com.campus.pickup.system.mapper.SysFinishApplyMapper;
import com.campus.pickup.system.service.ISysFinishApplyService;
import com.campus.pickup.system.service.ISysOrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 配送完成申请服务实现
 */
@Service
public class SysFinishApplyServiceImpl implements ISysFinishApplyService {

    @Autowired
    private SysFinishApplyMapper applyMapper;

    @Autowired
    private ISysOrderService orderService;

    @Override
    public List<SysFinishApply> selectList(SysFinishApply apply) {
        return applyMapper.selectList(apply);
    }

    @Override
    public SysFinishApply selectById(Long applyId) {
        return applyMapper.selectById(applyId);
    }

    @Override
    public int insert(SysFinishApply apply) {
        return applyMapper.insert(apply);
    }

    @Override
    @Transactional
    public int audit(Long applyId, boolean approve, String remark, Long auditUserId) {
        SysFinishApply apply = applyMapper.selectById(applyId);
        if (apply == null) {
            throw new ServiceException("申请不存在");
        }
        if (!"0".equals(apply.getApplyStatus())) {
            throw new ServiceException("该申请已审核");
        }
        String status = approve ? "1" : "2";
        if (approve) {
            orderService.finishOrder(apply.getOrderId());
        }
        return applyMapper.audit(applyId, status, remark, auditUserId);
    }
}
