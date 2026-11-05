package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysCreditApply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 信誉分扣除申请 Mapper
 */
public interface SysCreditApplyMapper {

    @Select("<script>SELECT a.*, u.user_name AS user_name FROM sys_credit_apply a " +
            "LEFT JOIN sys_user u ON a.user_id = u.user_id WHERE 1=1 " +
            "<if test='applyStatus != null and applyStatus != \"\"'>AND a.apply_status = #{applyStatus}</if> " +
            "<if test='userId != null'>AND a.user_id = #{userId}</if> " +
            "ORDER BY a.apply_id DESC</script>")
    List<SysCreditApply> selectList(SysCreditApply apply);

    @Select("SELECT * FROM sys_credit_apply WHERE apply_id = #{applyId}")
    SysCreditApply selectById(Long applyId);

    @Insert("INSERT INTO sys_credit_apply(user_id, change_score, reason, apply_status, apply_user_id, create_time) " +
            "VALUES(#{userId}, #{changeScore}, #{reason}, '0', #{applyUserId}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "applyId")
    int insert(SysCreditApply apply);

    /** 审核（通过/驳回） */
    @Update("UPDATE sys_credit_apply SET apply_status=#{applyStatus}, audit_user_id=#{auditUserId}, " +
            "audit_time=now(), remark=#{remark} WHERE apply_id=#{applyId} AND apply_status='0'")
    int audit(@Param("applyId") Long applyId,
              @Param("applyStatus") String applyStatus,
              @Param("auditUserId") Long auditUserId,
              @Param("remark") String remark);
}
