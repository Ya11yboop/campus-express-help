package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysComplaint;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 申诉/投诉 Mapper
 */
public interface SysComplaintMapper {

    @Select("<script>SELECT c.*, o.order_no AS order_no, u1.user_name AS user_name, u2.user_name AS target_user_name " +
            "FROM sys_complaint c " +
            "LEFT JOIN sys_order o ON c.order_id = o.order_id " +
            "LEFT JOIN sys_user u1 ON c.user_id = u1.user_id " +
            "LEFT JOIN sys_user u2 ON c.target_user_id = u2.user_id " +
            "WHERE 1=1 " +
            "<if test='orderId != null'>AND c.order_id = #{orderId}</if> " +
            "<if test='schoolId != null'>AND c.school_id = #{schoolId}</if> " +
            "<if test='userId != null'>AND c.user_id = #{userId}</if> " +
            "<if test='complaintType != null and complaintType != \"\"'>AND c.complaint_type = #{complaintType}</if> " +
            "<if test='handleStatus != null and handleStatus != \"\"'>AND c.handle_status = #{handleStatus}</if> " +
            "ORDER BY c.complaint_id DESC</script>")
    List<SysComplaint> selectComplaintList(SysComplaint complaint);

    @Select("SELECT * FROM sys_complaint WHERE complaint_id = #{complaintId}")
    SysComplaint selectComplaintById(Long complaintId);

    @Insert("INSERT INTO sys_complaint(order_id, school_id, user_id, target_user_id, complaint_type, content, images, handle_status, handle_result, handler_id, handle_time, create_time, remark) " +
            "VALUES(#{orderId}, #{schoolId}, #{userId}, #{targetUserId}, #{complaintType}, #{content}, #{images}, #{handleStatus}, #{handleResult}, #{handlerId}, #{handleTime}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "complaintId")
    int insertComplaint(SysComplaint complaint);

    @Update("UPDATE sys_complaint SET handle_status=#{handleStatus}, handle_result=#{handleResult}, handler_id=#{handlerId}, handle_time=#{handleTime} WHERE complaint_id=#{complaintId}")
    int handleComplaint(SysComplaint complaint);

    @Delete("DELETE FROM sys_complaint WHERE complaint_id = #{complaintId}")
    int deleteComplaintById(Long complaintId);

    @Select("<script>SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS day, COUNT(*) AS cnt FROM sys_complaint " +
            "WHERE create_time >= #{startDate} " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')</script>")
    List<Map<String, Object>> countComplaintByDay(@Param("startDate") String startDate, @Param("schoolId") Long schoolId);
}
