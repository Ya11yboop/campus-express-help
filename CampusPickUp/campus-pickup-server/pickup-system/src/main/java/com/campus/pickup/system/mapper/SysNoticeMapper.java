package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysNotice;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 公告 Mapper
 */
public interface SysNoticeMapper {

    @Select("<script>SELECT * FROM sys_notice WHERE 1=1 " +
            "<if test='title != null and title != \"\"'>AND title LIKE CONCAT('%', #{title}, '%')</if> " +
            "<if test='noticeType != null and noticeType != \"\"'>AND notice_type = #{noticeType}</if> " +
            "<if test='schoolId != null'>AND school_id = #{schoolId}</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY notice_id DESC</script>")
    List<SysNotice> selectNoticeList(SysNotice notice);

    @Select("SELECT * FROM sys_notice WHERE notice_id = #{noticeId}")
    SysNotice selectNoticeById(Long noticeId);

    @Select("<script>SELECT * FROM sys_notice WHERE status = '0' <if test='schoolId != null'>AND school_id = #{schoolId}</if> ORDER BY notice_id DESC</script>")
    List<SysNotice> selectNoticeAll(@Param("schoolId") Long schoolId);

    @Insert("INSERT INTO sys_notice(title, content, notice_type, school_id, status, create_time, remark) " +
            "VALUES(#{title}, #{content}, #{noticeType}, #{schoolId}, #{status}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "noticeId")
    int insertNotice(SysNotice notice);

    @Update("UPDATE sys_notice SET title=#{title}, content=#{content}, notice_type=#{noticeType}, " +
            "status=#{status}, update_time=now(), remark=#{remark} WHERE notice_id=#{noticeId}")
    int updateNotice(SysNotice notice);

    @Delete("DELETE FROM sys_notice WHERE notice_id = #{noticeId}")
    int deleteNoticeById(Long noticeId);
}
