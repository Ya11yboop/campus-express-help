package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysFinishApply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 配送完成申请 Mapper
 */
public interface SysFinishApplyMapper {

    @Select("<script>SELECT a.*, o.order_no AS order_no FROM sys_finish_apply a " +
            "LEFT JOIN sys_order o ON a.order_id = o.order_id WHERE 1=1 " +
            "<if test='applyStatus != null and applyStatus != \"\"'>AND a.apply_status = #{applyStatus}</if> " +
            "ORDER BY a.apply_id DESC</script>")
    List<SysFinishApply> selectList(SysFinishApply apply);

    @Select("SELECT * FROM sys_finish_apply WHERE apply_id = #{applyId}")
    SysFinishApply selectById(Long applyId);

    @Insert("INSERT INTO sys_finish_apply(order_id, courier_id, image, apply_status, create_time) " +
            "VALUES(#{orderId}, #{courierId}, #{image}, '0', now())")
    @Options(useGeneratedKeys = true, keyProperty = "applyId")
    int insert(SysFinishApply apply);

    @Update("UPDATE sys_finish_apply SET apply_status=#{applyStatus}, remark=#{remark}, audit_user_id=#{auditUserId}, audit_time=now() " +
            "WHERE apply_id=#{applyId} AND apply_status='0'")
    int audit(@Param("applyId") Long applyId,
              @Param("applyStatus") String applyStatus,
              @Param("remark") String remark,
              @Param("auditUserId") Long auditUserId);
}
