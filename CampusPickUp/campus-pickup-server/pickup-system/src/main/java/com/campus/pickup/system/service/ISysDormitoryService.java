package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysDormitory;

import java.util.List;

/**
 * 宿舍楼服务接口
 */
public interface ISysDormitoryService {

    List<SysDormitory> selectDormitoryList(SysDormitory dormitory);

    SysDormitory selectDormitoryById(Long dormitoryId);

    List<SysDormitory> selectDormitoryAll(Long schoolId);

    int insertDormitory(SysDormitory dormitory);

    int updateDormitory(SysDormitory dormitory);

    int deleteDormitoryById(Long dormitoryId);
}
