package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysSchool;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 学校 Mapper
 */
public interface SysSchoolMapper {

    @Select("<script>SELECT * FROM sys_school WHERE 1=1 " +
            "<if test='schoolName != null and schoolName != \"\"'>AND school_name LIKE CONCAT('%', #{schoolName}, '%')</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY school_id</script>")
    List<SysSchool> selectSchoolList(SysSchool school);

    @Select("SELECT * FROM sys_school WHERE school_id = #{schoolId}")
    SysSchool selectSchoolById(Long schoolId);

    @Select("SELECT * FROM sys_school WHERE status = '0' ORDER BY school_id")
    List<SysSchool> selectSchoolAll();

    @Insert("INSERT INTO sys_school(school_name, school_code, address, status, create_time, remark) " +
            "VALUES(#{schoolName}, #{schoolCode}, #{address}, #{status}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "schoolId")
    int insertSchool(SysSchool school);

    @Update("UPDATE sys_school SET school_name=#{schoolName}, school_code=#{schoolCode}, address=#{address}, " +
            "status=#{status}, update_time=now(), remark=#{remark} WHERE school_id=#{schoolId}")
    int updateSchool(SysSchool school);

    @Delete("DELETE FROM sys_school WHERE school_id = #{schoolId}")
    int deleteSchoolById(Long schoolId);

    /** 统计该学校下的用户数（删除校验用） */
    @Select("SELECT COUNT(*) FROM sys_user WHERE school_id = #{schoolId}")
    int countUserBySchoolId(Long schoolId);
}
