package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysParcelSize;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 快递大小分类 Mapper
 */
public interface SysParcelSizeMapper {

    @Select("<script>SELECT * FROM sys_parcel_size WHERE 1=1 " +
            "<if test='sizeName != null and sizeName != \"\"'>AND size_name LIKE CONCAT('%', #{sizeName}, '%')</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY sort</script>")
    List<SysParcelSize> selectSizeList(SysParcelSize size);

    @Select("SELECT * FROM sys_parcel_size WHERE size_id = #{sizeId}")
    SysParcelSize selectSizeById(Long sizeId);

    @Select("SELECT * FROM sys_parcel_size WHERE status = '0' ORDER BY sort")
    List<SysParcelSize> selectSizeAll();

    @Insert("INSERT INTO sys_parcel_size(size_name, size_desc, fee, status, sort, create_time, remark) " +
            "VALUES(#{sizeName}, #{sizeDesc}, #{fee}, #{status}, #{sort}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "sizeId")
    int insertSize(SysParcelSize size);

    @Update("UPDATE sys_parcel_size SET size_name=#{sizeName}, size_desc=#{sizeDesc}, fee=#{fee}, " +
            "status=#{status}, sort=#{sort}, update_time=now(), remark=#{remark} WHERE size_id=#{sizeId}")
    int updateSize(SysParcelSize size);

    @Delete("DELETE FROM sys_parcel_size WHERE size_id = #{sizeId}")
    int deleteSizeById(Long sizeId);
}
