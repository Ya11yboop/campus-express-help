package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.context.UserContext;
import com.campus.pickup.system.domain.SysUserAddress;
import com.campus.pickup.system.service.ISysUserAddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * 用户收货地址控制器（每个用户管理自己的地址）
 */
@RestController
@RequestMapping("/system/address")
public class SysUserAddressController extends BaseController {

    @Autowired
    private ISysUserAddressService addressService;

    /** 当前用户的地址列表 */
    @GetMapping("/list")
    public AjaxResult list() {
        List<SysUserAddress> list = addressService.selectByUserId(UserContext.getUserId());
        return AjaxResult.success(list);
    }

    /** 新增地址 */
    @PostMapping
    public AjaxResult add(@RequestBody SysUserAddress address) {
        address.setAddressId(null);
        address.setUserId(UserContext.getUserId());
        return toAjax(addressService.insert(address));
    }

    /** 修改地址 */
    @PutMapping
    public AjaxResult edit(@RequestBody SysUserAddress address) {
        if (!isOwner(address.getAddressId())) {
            return AjaxResult.error("地址不存在");
        }
        address.setUserId(UserContext.getUserId());
        return toAjax(addressService.update(address));
    }

    /** 删除地址 */
    @DeleteMapping("/{addressId}")
    public AjaxResult remove(@PathVariable Long addressId) {
        if (!isOwner(addressId)) {
            return AjaxResult.error("地址不存在");
        }
        return toAjax(addressService.deleteById(addressId));
    }

    /** 设为默认地址 */
    @PutMapping("/default/{addressId}")
    public AjaxResult setDefault(@PathVariable Long addressId) {
        if (!isOwner(addressId)) {
            return AjaxResult.error("地址不存在");
        }
        return toAjax(addressService.setDefault(UserContext.getUserId(), addressId));
    }

    /** 校验地址是否属于当前用户 */
    private boolean isOwner(Long addressId) {
        if (addressId == null) {
            return false;
        }
        SysUserAddress exist = addressService.selectById(addressId);
        return exist != null && exist.getUserId() != null && exist.getUserId().equals(UserContext.getUserId());
    }
}
