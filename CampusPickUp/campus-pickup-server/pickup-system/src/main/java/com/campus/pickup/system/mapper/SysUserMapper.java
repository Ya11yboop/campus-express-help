package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysUser;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;
import java.util.Map;

/**
 * 用户 Mapper（注解式）
 *
 * @author campus-pickup
 */
public interface SysUserMapper {

    @Select("SELECT * FROM sys_user WHERE user_name = #{userName}")
    SysUser selectUserByUserName(String userName);

    @Select("SELECT * FROM sys_user WHERE user_id = #{userId}")
    SysUser selectUserById(Long userId);

    @Select("SELECT * FROM sys_user WHERE openid = #{openid}")
    SysUser selectUserByOpenId(String openid);

    @Select("<script>SELECT * FROM sys_user WHERE 1=1 " +
            "<if test='userName != null and userName != \"\"'>AND user_name LIKE CONCAT('%', #{userName}, '%')</if> " +
            "<if test='realName != null and realName != \"\"'>AND real_name LIKE CONCAT('%', #{realName}, '%')</if> " +
            "<if test='studentNo != null and studentNo != \"\"'>AND student_no = #{studentNo}</if> " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "<if test='phone != null and phone != \"\"'>AND phone LIKE CONCAT('%', #{phone}, '%')</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "<if test='userType != null and userType != \"\"'>AND user_type = #{userType}</if> " +
            "ORDER BY user_id DESC</script>")
    List<SysUser> selectUserList(SysUser user);

    @Insert("INSERT INTO sys_user(openid, user_name, nick_name, real_name, password, school, school_id, student_no, id_card, phone, email, avatar, gender, dormitory_id, room_no, credit_score, status, user_type, create_time, remark) " +
            "VALUES(#{openid}, #{userName}, #{nickName}, #{realName}, #{password}, #{school}, #{schoolId}, #{studentNo}, #{idCard}, #{phone}, #{email}, #{avatar}, #{gender}, #{dormitoryId}, #{roomNo}, #{creditScore}, #{status}, #{userType}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "userId")
    int insertUser(SysUser user);

    @Update("<script>UPDATE sys_user <set>" +
            "<if test='userName != null'>user_name=#{userName},</if>" +
            "<if test='password != null'>password=#{password},</if>" +
            "<if test='nickName != null'>nick_name=#{nickName},</if>" +
            "<if test='realName != null'>real_name=#{realName},</if>" +
            "<if test='school != null'>school=#{school},</if>" +
            "<if test='schoolId != null'>school_id=#{schoolId},</if>" +
            "<if test='studentNo != null'>student_no=#{studentNo},</if>" +
            "<if test='idCard != null'>id_card=#{idCard},</if>" +
            "<if test='phone != null'>phone=#{phone},</if>" +
            "<if test='email != null'>email=#{email},</if>" +
            "<if test='avatar != null'>avatar=#{avatar},</if>" +
            "<if test='gender != null'>gender=#{gender},</if>" +
            "<if test='dormitoryId != null'>dormitory_id=#{dormitoryId},</if>" +
            "<if test='roomNo != null'>room_no=#{roomNo},</if>" +
            "<if test='creditScore != null'>credit_score=#{creditScore},</if>" +
            "<if test='status != null'>status=#{status},</if>" +
            "<if test='userType != null'>user_type=#{userType},</if>" +
            "<if test='remark != null'>remark=#{remark},</if>" +
            "update_time=now()</set> WHERE user_id=#{userId}</script>")
    int updateUser(SysUser user);

    @Update("UPDATE sys_user SET credit_score=#{creditScore}, update_time=now() WHERE user_id=#{userId}")
    int updateCreditScore(@Param("userId") Long userId, @Param("creditScore") Integer creditScore);

    @Delete("DELETE FROM sys_user WHERE user_id = #{userId}")
    int deleteUserById(Long userId);

    @Insert("INSERT INTO sys_user_role(user_id, role_id) VALUES(#{userId}, #{roleId})")
    int insertUserRole(@Param("userId") Long userId, @Param("roleId") Long roleId);

    @Select("<script>SELECT DATE_FORMAT(create_time, '%Y-%m-%d') AS day, COUNT(*) AS cnt FROM sys_user " +
            "WHERE create_time >= #{startDate} " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "GROUP BY DATE_FORMAT(create_time, '%Y-%m-%d')</script>")
    List<Map<String, Object>> countUserByDay(@Param("startDate") String startDate, @Param("schoolId") Long schoolId);
}
