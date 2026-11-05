package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysEvaluate;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 评价 Mapper
 */
public interface SysEvaluateMapper {

    @Select("<script>SELECT * FROM sys_evaluate WHERE 1=1 " +
            "<if test='orderId != null'>AND order_id = #{orderId}</if> " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "<if test='courierId != null'>AND courier_id = #{courierId}</if> " +
            "<if test='userId != null'>AND user_id = #{userId}</if> " +
            "ORDER BY evaluate_id DESC</script>")
    List<SysEvaluate> selectEvaluateList(SysEvaluate evaluate);

    @Select("SELECT * FROM sys_evaluate WHERE evaluate_id = #{evaluateId}")
    SysEvaluate selectEvaluateById(Long evaluateId);

    @Insert("INSERT INTO sys_evaluate(order_id, school_id, user_id, courier_id, score, content, reply, reply_time, create_time) " +
            "VALUES(#{orderId}, #{schoolId}, #{userId}, #{courierId}, #{score}, #{content}, #{reply}, #{replyTime}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "evaluateId")
    int insertEvaluate(SysEvaluate evaluate);

    @Update("UPDATE sys_evaluate SET reply=#{reply}, reply_time=now() WHERE evaluate_id=#{evaluateId}")
    int replyEvaluate(SysEvaluate evaluate);

    @Delete("DELETE FROM sys_evaluate WHERE evaluate_id = #{evaluateId}")
    int deleteEvaluateById(Long evaluateId);

    @Select("<script>SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS day, COUNT(*) AS cnt FROM sys_evaluate " +
            "WHERE create_time >= #{startDate} " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')</script>")
    List<Map<String, Object>> countEvaluateByDay(@Param("startDate") String startDate, @Param("schoolId") Long schoolId);
}
