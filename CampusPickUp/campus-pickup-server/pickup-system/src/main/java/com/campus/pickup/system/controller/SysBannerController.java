package com.campus.pickup.system.controller;

import com.campus.pickup.common.core.controller.BaseController;
import com.campus.pickup.common.core.domain.AjaxResult;
import com.campus.pickup.framework.annotation.RequiresRole;
import com.campus.pickup.system.domain.SysBanner;
import com.campus.pickup.system.service.ISysBannerService;
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
 * 轮播图控制器
 */
@RestController
@RequestMapping("/banner")
public class SysBannerController extends BaseController {

    @Autowired
    private ISysBannerService bannerService;

    /** 启用轮播图列表（所有登录用户可访问，小程序用） */
    @GetMapping("/listAll")
    public AjaxResult listAll() {
        return AjaxResult.success(bannerService.selectBannerAll());
    }

    @RequiresRole("operation_admin")
    @GetMapping("/list")
    public AjaxResult list(SysBanner banner) {
        startPage();
        List<SysBanner> list = bannerService.selectBannerList(banner);
        return getDataTable(list);
    }

    @RequiresRole("operation_admin")
    @GetMapping("/{bannerId}")
    public AjaxResult getInfo(@PathVariable Long bannerId) {
        return AjaxResult.success(bannerService.selectBannerById(bannerId));
    }

    @RequiresRole("operation_admin")
    @PostMapping
    public AjaxResult add(@RequestBody SysBanner banner) {
        return toAjax(bannerService.insertBanner(banner));
    }

    @RequiresRole("operation_admin")
    @PutMapping
    public AjaxResult edit(@RequestBody SysBanner banner) {
        return toAjax(bannerService.updateBanner(banner));
    }

    @RequiresRole("operation_admin")
    @DeleteMapping("/{ids}")
    public AjaxResult remove(@PathVariable Long[] ids) {
        int total = 0;
        for (Long id : ids) {
            total += bannerService.deleteBannerById(id);
        }
        return toAjax(total);
    }
}
