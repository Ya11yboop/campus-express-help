package com.campus.pickup.system.mapper;

import com.campus.pickup.system.domain.SysRole;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Options;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import java.util.List;

/**
 * 角色 Mapper
 *
 * @author campus-pickup
 */
public interface SysRoleMapper {

    @Select("<script>SELECT role_id, role_name, role_key, role_sort, status, create_time, update_time, remark " +
            "FROM sys_role WHERE 1=1 " +
            "<if test='roleName != null and roleName != \"\"'>AND role_name LIKE CONCAT('%', #{roleName}, '%')</if> " +
            "<if test='roleKey != null and roleKey != \"\"'>AND role_key = #{roleKey}</if> " +
            "<if test='status != null and status != \"\"'>AND status = #{status}</if> " +
            "ORDER BY role_sort</script>")
    List<SysRole> selectRoleList(SysRole role);

    @Select("SELECT role_id, role_name, role_key, role_sort, status, create_time, update_time, remark FROM sys_role WHERE role_id = #{roleId}")
    SysRole selectRoleById(Long roleId);

    @Insert("INSERT INTO sys_role(role_name, role_key, role_sort, status, create_time, remark) " +
            "VALUES(#{roleName}, #{roleKey}, #{roleSort}, #{status}, now(), #{remark})")
    @Options(useGeneratedKeys = true, keyProperty = "roleId")
    int insertRole(SysRole role);

    @Update("UPDATE sys_role SET role_name=#{roleName}, role_key=#{roleKey}, role_sort=#{roleSort}, status=#{status}, update_time=now(), remark=#{remark} WHERE role_id=#{roleId}")
    int updateRole(SysRole role);

    @Delete("DELETE FROM sys_role WHERE role_id = #{roleId}")
    int deleteRoleById(Long roleId);

    @Select("SELECT r.role_key FROM sys_role r JOIN sys_user_role ur ON r.role_id = ur.role_id " +
            "WHERE ur.user_id = #{userId} AND r.status = '0'")
    List<String> selectRoleKeysByUserId(Long userId);

    @Select("SELECT role_id FROM sys_role WHERE role_key = #{roleKey} LIMIT 1")
    Long selectRoleIdByKey(String roleKey);
}
