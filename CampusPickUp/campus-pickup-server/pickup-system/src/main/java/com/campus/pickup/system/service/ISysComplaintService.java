package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysComplaint;

import java.util.List;

/**
 * 申诉/投诉服务接口
 */
public interface ISysComplaintService {

    List<SysComplaint> selectComplaintList(SysComplaint complaint);

    SysComplaint selectComplaintById(Long complaintId);

    int insertComplaint(SysComplaint complaint);

    int handleComplaint(SysComplaint complaint);

    int deleteComplaintById(Long complaintId);
}
