package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysRole;

import java.util.List;
import java.util.Set;

/**
 * 角色服务接口
 *
 * @author campus-pickup
 */
public interface ISysRoleService {

    List<SysRole> selectRoleList(SysRole role);

    SysRole selectRoleById(Long roleId);

    int insertRole(SysRole role);

    int updateRole(SysRole role);

    int deleteRoleById(Long roleId);

    Set<String> selectRoleKeysByUserId(Long userId);

    Long selectRoleIdByKey(String roleKey);
}
