package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysParcelSize;

import java.util.List;

/**
 * 快递大小分类服务接口
 */
public interface ISysParcelSizeService {

    List<SysParcelSize> selectSizeList(SysParcelSize size);

    SysParcelSize selectSizeById(Long sizeId);

    List<SysParcelSize> selectSizeAll();

    int insertSize(SysParcelSize size);

    int updateSize(SysParcelSize size);

    int deleteSizeById(Long sizeId);
}
