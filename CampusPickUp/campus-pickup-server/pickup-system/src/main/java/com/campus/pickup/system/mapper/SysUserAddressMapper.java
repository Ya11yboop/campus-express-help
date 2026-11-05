package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysUserAddress;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 用户收货地址 Mapper
 */
public interface SysUserAddressMapper {

    @Select("SELECT * FROM sys_user_address WHERE user_id = #{userId} ORDER BY is_default DESC, address_id DESC")
    List<SysUserAddress> selectByUserId(Long userId);

    @Select("SELECT * FROM sys_user_address WHERE address_id = #{addressId}")
    SysUserAddress selectById(Long addressId);

    @Insert("INSERT INTO sys_user_address(user_id, contact_name, contact_phone, address, is_default, create_time) " +
            "VALUES(#{userId}, #{contactName}, #{contactPhone}, #{address}, #{isDefault}, now())")
    @Options(useGeneratedKeys = true, keyProperty = "addressId")
    int insert(SysUserAddress address);

    @Update("UPDATE sys_user_address SET contact_name=#{contactName}, contact_phone=#{contactPhone}, " +
            "address=#{address}, is_default=#{isDefault}, update_time=now() WHERE address_id=#{addressId}")
    int update(SysUserAddress address);

    @Delete("DELETE FROM sys_user_address WHERE address_id = #{addressId}")
    int deleteById(Long addressId);

    /** 取消某用户其它地址的默认标记 */
    @Update("UPDATE sys_user_address SET is_default='0' WHERE user_id = #{userId}")
    int clearDefaultByUserId(@Param("userId") Long userId);

    /** 设置某地址为默认 */
    @Update("UPDATE sys_user_address SET is_default='1' WHERE address_id = #{addressId}")
    int setDefaultById(@Param("addressId") Long addressId);
}
