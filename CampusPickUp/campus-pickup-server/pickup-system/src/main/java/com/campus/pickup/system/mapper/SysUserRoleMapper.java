package com.campus.pickup.system.mapper;

import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 用户角色关联 Mapper
 *
 * @author campus-pickup
 */
public interface SysUserRoleMapper {

    @Insert("<script>INSERT INTO sys_user_role(user_id, role_id) VALUES " +
            "<foreach collection='roleIds' item='roleId' separator=','>(#{userId}, #{roleId})</foreach></script>")
    int insertUserRoles(@Param("userId") Long userId, @Param("roleIds") List<Long> roleIds);

    @Delete("DELETE FROM sys_user_role WHERE user_id = #{userId}")
    int deleteUserRoleByUserId(Long userId);

    @Select("SELECT role_id FROM sys_user_role WHERE user_id = #{userId}")
    List<Long> selectRoleIdsByUserId(Long userId);
}
