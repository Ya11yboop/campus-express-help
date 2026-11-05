package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysOrderWithdrawApply;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 接单撤回申请 Mapper
 */
public interface SysOrderWithdrawApplyMapper {

    @Select("<script>SELECT a.*, o.order_no AS order_no, u.user_name AS courier_name " +
            "FROM sys_order_withdraw_apply a " +
            "LEFT JOIN sys_order o ON a.order_id = o.order_id " +
            "LEFT JOIN sys_user u ON a.courier_id = u.user_id WHERE 1=1 " +
            "<if test='applyStatus != null and applyStatus != \"\"'>AND a.apply_status = #{applyStatus}</if> " +
            "<if test='orderId != null'>AND a.order_id = #{orderId}</if> " +
            "ORDER BY a.apply_id DESC</script>")
    List<SysOrderWithdrawApply> selectList(SysOrderWithdrawApply apply);

    @Select("SELECT * FROM sys_order_withdraw_apply WHERE apply_id = #{applyId}")
    SysOrderWithdrawApply selectById(Long applyId);

    @Insert("INSERT INTO sys_order_withdraw_apply(order_id, courier_id, reason, apply_status, create_time) " +
            "VALUES(#{orderId}, #{courierId}, #{reason}, '0', now())")
    @Options(useGeneratedKeys = true, keyProperty = "applyId")
    int insert(SysOrderWithdrawApply apply);

    @Update("UPDATE sys_order_withdraw_apply SET apply_status=#{applyStatus}, audit_user_id=#{auditUserId}, " +
            "audit_time=now(), remark=#{remark} WHERE apply_id=#{applyId} AND apply_status='0'")
    int audit(@Param("applyId") Long applyId,
              @Param("applyStatus") String applyStatus,
              @Param("auditUserId") Long auditUserId,
              @Param("remark") String remark);
}
