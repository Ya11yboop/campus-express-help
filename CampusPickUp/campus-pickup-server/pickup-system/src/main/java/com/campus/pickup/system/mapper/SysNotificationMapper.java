package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysNotification;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户通知 Mapper
 */
public interface SysNotificationMapper {

    @Select("SELECT * FROM sys_notification WHERE user_id = #{userId} ORDER BY notif_id DESC")
    List<SysNotification> selectByUserId(Long userId);

    @Select("SELECT COUNT(*) FROM sys_notification WHERE user_id = #{userId} AND read_status = '0'")
    int countUnread(Long userId);

    @Insert("INSERT INTO sys_notification(user_id, title, content, read_status, create_time) " +
            "VALUES(#{userId}, #{title}, #{content}, '0', now())")
    @Options(useGeneratedKeys = true, keyProperty = "notifId")
    int insert(SysNotification notification);

    @Update("UPDATE sys_notification SET read_status='1' WHERE notif_id = #{notifId} AND user_id = #{userId}")
    int markRead(@Param("notifId") Long notifId, @Param("userId") Long userId);
}
