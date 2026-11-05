package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysOrder;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 订单 Mapper
 */
public interface SysOrderMapper {

    @Select("<script>SELECT o.*, u1.user_name AS user_name, u2.user_name AS courier_name " +
            "FROM sys_order o " +
            "LEFT JOIN sys_user u1 ON o.user_id = u1.user_id " +
            "LEFT JOIN sys_user u2 ON o.courier_id = u2.user_id " +
            "WHERE 1=1 " +
            "<if test='orderNo != null and orderNo != \"\"'>AND o.order_no LIKE CONCAT('%', #{orderNo}, '%')</if> " +
            "<if test='userId != null'>AND o.user_id = #{userId}</if> " +
            "<if test='courierId != null'>AND o.courier_id = #{courierId}</if> " +
            "<if test='orderType != null and orderType != \"\"'>AND o.order_type = #{orderType}</if> " +
            "<if test='orderStatus != null and orderStatus != \"\"'>AND o.order_status = #{orderStatus}</if> " +
            "<if test='schoolId != null'>AND o.school_id = #{schoolId}</if> " +
            "<if test='pointId != null'>AND o.point_id = #{pointId}</if> " +
            "<if test='deliveryAddress != null and deliveryAddress != \"\"'>AND o.delivery_address LIKE CONCAT('%', #{deliveryAddress}, '%')</if> " +
            "<if test='feeMin != null'>AND o.fee &gt;= #{feeMin}</if> " +
            "<if test='feeMax != null'>AND o.fee &lt;= #{feeMax}</if> " +
            "ORDER BY o.order_id DESC</script>")
    List<SysOrder> selectOrderList(SysOrder order);

    @Select("SELECT o.*, u1.user_name AS user_name, u2.user_name AS courier_name FROM sys_order o " +
            "LEFT JOIN sys_user u1 ON o.user_id = u1.user_id " +
            "LEFT JOIN sys_user u2 ON o.courier_id = u2.user_id WHERE o.order_id = #{orderId}")
    SysOrder selectOrderById(Long orderId);

    @Insert("INSERT INTO sys_order(order_no, user_id, courier_id, order_type, tracking_no, express_company, size_id, point_id, school_id, " +
            "pickup_address, delivery_address, receiver_name, receiver_phone, fee, order_status, create_time, accept_time, finish_time, remark) " +
            "VALUES(#{orderNo}, #{userId}, #{courierId}, #{orderType}, #{trackingNo}, #{expressCompany}, #{sizeId}, #{pointId}, #{schoolId}, " +
            "#{pickupAddress}, #{deliveryAddress}, #{receiverName}, #{receiverPhone}, #{fee}, #{orderStatus}, #{createTime}, #{acceptTime}, #{finishTime}, #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "orderId")
    int insertOrder(SysOrder order);

    @Update("<script>UPDATE sys_order <set>order_id=#{orderId}," +
            "<if test='courierId != null'>courier_id=#{courierId},</if>" +
            "<if test='orderType != null'>order_type=#{orderType},</if>" +
            "<if test='trackingNo != null'>tracking_no=#{trackingNo},</if>" +
            "<if test='expressCompany != null'>express_company=#{expressCompany},</if>" +
            "<if test='sizeId != null'>size_id=#{sizeId},</if>" +
            "<if test='pointId != null'>point_id=#{pointId},</if>" +
            "<if test='pickupAddress != null'>pickup_address=#{pickupAddress},</if>" +
            "<if test='deliveryAddress != null'>delivery_address=#{deliveryAddress},</if>" +
            "<if test='receiverName != null'>receiver_name=#{receiverName},</if>" +
            "<if test='receiverPhone != null'>receiver_phone=#{receiverPhone},</if>" +
            "<if test='fee != null'>fee=#{fee},</if>" +
            "<if test='orderStatus != null'>order_status=#{orderStatus},</if>" +
            "<if test='remark != null'>remark=#{remark},</if>" +
            "</set> WHERE order_id=#{orderId}</script>")
    int updateOrder(SysOrder order);

    @Update("UPDATE sys_order SET courier_id=#{courierId}, order_status='1', accept_time=now() WHERE order_id=#{orderId} AND order_status='0'")
    int acceptOrder(@Param("orderId") Long orderId, @Param("courierId") Long courierId);

    @Update("UPDATE sys_order SET order_status='2', finish_time=now() WHERE order_id=#{orderId}")
    int finishOrder(Long orderId);

    @Update("UPDATE sys_order SET order_status='3' WHERE order_id=#{orderId}")
    int cancelOrder(Long orderId);

    @Update("UPDATE sys_order SET courier_id=NULL, order_status='0', accept_time=NULL WHERE order_id=#{orderId} AND order_status='1'")
    int withdrawOrder(Long orderId);

    @Delete("DELETE FROM sys_order WHERE order_id = #{orderId}")
    int deleteOrderById(Long orderId);

    @Select("<script>SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS day, COUNT(*) AS cnt FROM sys_order " +
            "WHERE create_time >= #{startDate} " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')</script>")
    List<Map<String, Object>> countOrderByDay(@Param("startDate") String startDate, @Param("schoolId") Long schoolId);
}
