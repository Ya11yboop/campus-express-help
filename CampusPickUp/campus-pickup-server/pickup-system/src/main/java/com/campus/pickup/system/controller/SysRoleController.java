package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.system.domain.SysRole;
import com.campus.pickup.system.mapper.SysUserRoleMapper;
import com.campus.pickup.system.service.ISysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;

/**
 * 角色管理控制器
 *
 * @author campus-pickup
 */
@RestController
@RequestMapping("/system/role")
public class SysRoleController extends BaseController {

    @Autowired
    private ISysRoleService roleService;

    @Autowired
    private SysUserRoleMapper userRoleMapper;

    @RequiresRole("super_admin")
    @GetMapping("/list")
    public AjaxResult list(SysRole role) {
        startPage();
        List<SysRole> list = roleService.selectRoleList(role);
        return getDataTable(list);
    }

    /** 角色下拉选项 */
    @RequiresRole({"super_admin", "operation_admin"})
    @GetMapping("/optionselect")
    public AjaxResult optionselect() {
        return AjaxResult.success(roleService.selectRoleList(new SysRole()));
    }

    @RequiresRole("super_admin")
    @GetMapping("/{roleId}")
    public AjaxResult getInfo(@PathVariable Long roleId) {
        return AjaxResult.success(roleService.selectRoleById(roleId));
    }

    @RequiresRole("super_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysRole role) {
        return toAjax(roleService.insertRole(role));
    }

    @RequiresRole("super_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysRole role) {
        return toAjax(roleService.updateRole(role));
    }

    @RequiresRole("super_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += roleService.deleteRoleById(id);
        }
        return toAjax(total);
    }

    /** 查询用户已有角色 */
    @RequiresRole({"super_admin", "operation_admin"})
    @GetMapping("/user/{userId}")
    public AjaxResult getUserRoles(@PathVariable Long userId) {
        return AjaxResult.success(userRoleMapper.selectRoleIdsByUserId(userId));
    }

    /** 分配用户角色 */
    @RequiresRole({"super_admin", "operation_admin"})
    @PutMapping("/user/{userId}")
    public AjaxResult assignUserRoles(@PathVariable Long userId, @RequestBody Long[] roleIds) {
        userRoleMapper.deleteUserRoleByUserId(userId);
        if (roleIds != null && roleIds.length > 0) {
            userRoleMapper.insertUserRoles(userId, Arrays.asList(roleIds));
        }
        return AjaxResult.success();
    }
}
