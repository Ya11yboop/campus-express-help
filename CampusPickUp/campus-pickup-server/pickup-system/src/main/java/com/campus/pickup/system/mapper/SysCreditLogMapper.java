package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysCreditLog;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 信誉分变更记录 Mapper
 */
public interface SysCreditLogMapper {

    @Select("<script>SELECT * FROM sys_credit_log WHERE 1=1 " +
            "<if test='userId != null'>AND user_id = #{userId}</if> " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "ORDER BY log_id DESC</script>")
    List<SysCreditLog> selectLogList(SysCreditLog log);

    @Insert("INSERT INTO sys_credit_log(user_id, school_id, change_score, credit_score, reason, operator_id, create_time) " +
            "VALUES(#{userId}, #{schoolId}, #{changeScore}, #{creditScore}, #{reason}, #{operatorId}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "logId")
    int insertLog(SysCreditLog log);
}
