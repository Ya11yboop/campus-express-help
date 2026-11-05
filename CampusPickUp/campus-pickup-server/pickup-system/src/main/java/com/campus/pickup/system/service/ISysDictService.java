package com.campus.pickup.system.service;

import com.campus.pickup.system.domain.SysDictData;
import com.campus.pickup.system.domain.SysDictType;

import java.util.List;

/**
 * 数据字典服务接口
 */
public interface ISysDictService {

    List<SysDictType> selectDictTypeList(SysDictType dictType);

    SysDictType selectDictTypeById(Long dictId);

    int insertDictType(SysDictType dictType);

    int updateDictType(SysDictType dictType);

    int deleteDictTypeById(Long dictId);

    List<SysDictData> selectDictDataList(SysDictData dictData);

    List<SysDictData> selectDictDataByType(String dictType);

    SysDictData selectDictDataById(Long dictCode);

    int insertDictData(SysDictData dictData);

    int updateDictData(SysDictData dictData);

    int deleteDictDataById(Long dictCode);
}
