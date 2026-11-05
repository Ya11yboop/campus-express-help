package com.campus.pickup.system.service.impl;

import com.alibaba.fastjson2.JSON;
import com.campus.pickup.framework.redis.CacheConstants;
import com.campus.pickup.framework.redis.RedisCacheUtil;
import com.campus.pickup.system.domain.SysDictData;
import com.campus.pickup.system.domain.SysDictType;
import com.campus.pickup.system.mapper.SysDictDataMapper;
import com.campus.pickup.system.mapper.SysDictTypeMapper;
import com.campus.pickup.system.service.ISysDictService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 数据字典服务实现
 */
@Service
public class SysDictServiceImpl implements ISysDictService {

    @Autowired
    private SysDictTypeMapper dictTypeMapper;

    @Autowired
    private SysDictDataMapper dictDataMapper;

    @Autowired
    private RedisCacheUtil redisCache;

    @Override
    public List<SysDictType> selectDictTypeList(SysDictType dictType) {
        return dictTypeMapper.selectDictTypeList(dictType);
    }

    @Override
    public SysDictType selectDictTypeById(Long dictId) {
        return dictTypeMapper.selectDictTypeById(dictId);
    }

    @Override
    public int insertDictType(SysDictType dictType) {
        int rows = dictTypeMapper.insertDictType(dictType);
        redisCache.deleteByPrefix(CacheConstants.DICT);
        return rows;
    }

    @Override
    public int updateDictType(SysDictType dictType) {
        int rows = dictTypeMapper.updateDictType(dictType);
        redisCache.deleteByPrefix(CacheConstants.DICT);
        return rows;
    }

    @Override
    public int deleteDictTypeById(Long dictId) {
        int rows = dictTypeMapper.deleteDictTypeById(dictId);
        redisCache.deleteByPrefix(CacheConstants.DICT);
        return rows;
    }

    @Override
    public List<SysDictData> selectDictDataList(SysDictData dictData) {
        return dictDataMapper.selectDictDataList(dictData);
    }

    @Override
    public List<SysDictData> selectDictDataByType(String dictType) {
        String key = CacheConstants.DICT + "data:" + dictType;
        String cached = redisCache.get(key);
        if (cached != null) {
            return JSON.parseArray(cached, SysDictData.class);
        }
        List<SysDictData> list = dictDataMapper.selectDictDataByType(dictType);
        redisCache.set(key, list);
        return list;
    }

    @Override
    public SysDictData selectDictDataById(Long dictCode) {
        return dictDataMapper.selectDictDataById(dictCode);
    }

    @Override
    public int insertDictData(SysDictData dictData) {
        int rows = dictDataMapper.insertDictData(dictData);
        redisCache.deleteByPrefix(CacheConstants.DICT);
        return rows;
    }

    @Override
    public int updateDictData(SysDictData dictData) {
        int rows = dictDataMapper.updateDictData(dictData);
        redisCache.deleteByPrefix(CacheConstants.DICT);
        return rows;
    }

    @Override
    public int deleteDictDataById(Long dictCode) {
        int rows = dictDataMapper.deleteDictDataById(dictCode);
        redisCache.deleteByPrefix(CacheConstants.DICT);
        return rows;
    }
}
