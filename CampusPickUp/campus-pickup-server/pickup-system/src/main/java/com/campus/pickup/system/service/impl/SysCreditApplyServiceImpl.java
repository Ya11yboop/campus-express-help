package com.campus.pickup.system.service.impl;

import com.campus.pickup.common.exception.ServiceException;
import com.campus.pickup.system.domain.SysCreditApply;
import com.campus.pickup.system.mapper.SysCreditApplyMapper;
import com.campus.pickup.system.service.ISysCreditApplyService;
import com.campus.pickup.system.service.ISysCreditService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 信誉分扣除申请服务实现
 */
@Service
public class SysCreditApplyServiceImpl implements ISysCreditApplyService {

    @Autowired
    private SysCreditApplyMapper applyMapper;

    @Autowired
    private ISysCreditService creditService;

    @Override
    public List<SysCreditApply> selectList(SysCreditApply apply) {
        return applyMapper.selectList(apply);
    }

    @Override
    public SysCreditApply selectById(Long applyId) {
        return applyMapper.selectById(applyId);
    }

    @Override
    public int insert(SysCreditApply apply) {
        return applyMapper.insert(apply);
    }

    @Override
    @Transactional
    public int audit(Long applyId, boolean approve, Long auditUserId, String remark) {
        SysCreditApply apply = applyMapper.selectById(applyId);
        if (apply == null) {
            throw new ServiceException("申请不存在");
        }
        if (!"0".equals(apply.getApplyStatus())) {
            throw new ServiceException("该申请已审核，请勿重复操作");
        }
        String status = approve ? "1" : "2";
        if (approve) {
            // 通过后实际扣除信誉分（changeScore 为负数）
            creditService.changeScore(apply.getUserId(), apply.getChangeScore(), apply.getReason());
        }
        return applyMapper.audit(applyId, status, auditUserId, remark);
    }
}
