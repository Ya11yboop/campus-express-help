package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysBanner;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 轮播图 Mapper
 */
public interface SysBannerMapper {

    @Select("<script>SELECT * FROM sys_banner WHERE 1=1 " +
            "<if test='title != null and title != \"\"'>AND title LIKE CONCAT('%', #{title}, '%')</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY sort</script>")
    List<SysBanner> selectBannerList(SysBanner banner);

    @Select("SELECT * FROM sys_banner WHERE banner_id = #{bannerId}")
    SysBanner selectBannerById(Long bannerId);

    @Select("SELECT * FROM sys_banner WHERE status = '0' ORDER BY sort")
    List<SysBanner> selectBannerAll();

    @Insert("INSERT INTO sys_banner(title, image, link_type, link_url, status, sort, create_time, remark) " +
            "VALUES(#{title}, #{image}, #{linkType}, #{linkUrl}, #{status}, #{sort}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "bannerId")
    int insertBanner(SysBanner banner);

    @Update("UPDATE sys_banner SET title=#{title}, image=#{image}, link_type=#{linkType}, link_url=#{linkUrl}, " +
            "status=#{status}, sort=#{sort}, update_time=now(), remark=#{remark} WHERE banner_id=#{bannerId}")
    int updateBanner(SysBanner banner);

    @Delete("DELETE FROM sys_banner WHERE banner_id = #{bannerId}")
    int deleteBannerById(Long bannerId);
}
