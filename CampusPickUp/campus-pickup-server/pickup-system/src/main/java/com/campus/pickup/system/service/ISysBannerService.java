package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysBanner;

import java.util.List;

/**
 * 轮播图服务接口
 */
public interface ISysBannerService {

    List<SysBanner> selectBannerList(SysBanner banner);

    SysBanner selectBannerById(Long bannerId);

    List<SysBanner> selectBannerAll();

    int insertBanner(SysBanner banner);

    int updateBanner(SysBanner banner);

    int deleteBannerById(Long bannerId);
}
