package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysDormitory;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 宿舍楼 Mapper
 */
public interface SysDormitoryMapper {

    @Select("<script>SELECT * FROM sys_dormitory WHERE 1=1 " +
            "<if test='dormitoryName != null and dormitoryName != \"\"'>AND dormitory_name LIKE CONCAT('%', #{dormitoryName}, '%')</if> " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY dormitory_id</script>")
    List<SysDormitory> selectDormitoryList(SysDormitory dormitory);

    @Select("SELECT * FROM sys_dormitory WHERE dormitory_id = #{dormitoryId}")
    SysDormitory selectDormitoryById(Long dormitoryId);

    @Select("<script>SELECT * FROM sys_dormitory WHERE status = '0' <if test='schoolId != null'>AND school_id = #{schoolId}</if> ORDER BY dormitory_id</script>")
    List<SysDormitory> selectDormitoryAll(@Param("schoolId") Long schoolId);

    @Insert("INSERT INTO sys_dormitory(dormitory_name, school_id, building_no, floor_count, location, status, create_time, remark) " +
            "VALUES(#{dormitoryName}, #{schoolId}, #{buildingNo}, #{floorCount}, #{location}, #{status}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "dormitoryId")
    int insertDormitory(SysDormitory dormitory);

    @Update("UPDATE sys_dormitory SET dormitory_name=#{dormitoryName}, building_no=#{buildingNo}, floor_count=#{floorCount}, " +
            "location=#{location}, status=#{status}, update_time=now(), remark=#{remark} WHERE dormitory_id=#{dormitoryId}")
    int updateDormitory(SysDormitory dormitory);

    @Delete("DELETE FROM sys_dormitory WHERE dormitory_id = #{dormitoryId}")
    int deleteDormitoryById(Long dormitoryId);
}
