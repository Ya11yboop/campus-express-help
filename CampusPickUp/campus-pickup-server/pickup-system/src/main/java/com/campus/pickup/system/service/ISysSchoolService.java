package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysSchool;

import java.util.List;

/**
 * 学校服务接口
 */
public interface ISysSchoolService {

    List<SysSchool> selectSchoolList(SysSchool school);

    SysSchool selectSchoolById(Long schoolId);

    List<SysSchool> selectSchoolAll();

    int insertSchool(SysSchool school);

    int updateSchool(SysSchool school);

    int deleteSchoolById(Long schoolId);
}
