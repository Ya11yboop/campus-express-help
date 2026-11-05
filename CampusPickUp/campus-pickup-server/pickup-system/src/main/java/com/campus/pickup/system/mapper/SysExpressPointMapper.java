package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysExpressPoint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 驿站 Mapper
 */
public interface SysExpressPointMapper {

    @Select("<script>SELECT * FROM sys_express_point WHERE 1=1 " +
            "<if test='pointName != null and pointName != \"\"'>AND point_name LIKE CONCAT('%', #{pointName}, '%')</if> " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY sort</script>")
    List<SysExpressPoint> selectPointList(SysExpressPoint point);

    @Select("SELECT * FROM sys_express_point WHERE point_id = #{pointId}")
    SysExpressPoint selectPointById(Long pointId);

    @Select("<script>SELECT * FROM sys_express_point WHERE status = '0' <if test='schoolId != null'>AND school_id = #{schoolId}</if> ORDER BY sort</script>")
    List<SysExpressPoint> selectPointAll(@Param("schoolId") Long schoolId);

    @Insert("INSERT INTO sys_express_point(point_name, school_id, location, manager_name, phone, status, sort, create_time, remark) " +
            "VALUES(#{pointName}, #{schoolId}, #{location}, #{managerName}, #{phone}, #{status}, #{sort}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "pointId")
    int insertPoint(SysExpressPoint point);

    @Update("UPDATE sys_express_point SET point_name=#{pointName}, location=#{location}, manager_name=#{managerName}, " +
            "phone=#{phone}, status=#{status}, sort=#{sort}, update_time=now(), remark=#{remark} WHERE point_id=#{pointId}")
    int updatePoint(SysExpressPoint point);

    @Delete("DELETE FROM sys_express_point WHERE point_id = #{pointId}")
    int deletePointById(Long pointId);
}
