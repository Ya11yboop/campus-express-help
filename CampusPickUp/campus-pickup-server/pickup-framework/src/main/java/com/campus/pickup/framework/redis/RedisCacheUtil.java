package com.campus.pickup.framework.redis;

import com.alibaba.fastjson2.JSON;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis 缓存工具（基于 StringRedisTemplate + fastjson2）
 *
 * @author campus-pickup
 */
@Component
public class RedisCacheUtil {

    @Autowired
    private StringRedisTemplate redisTemplate;

    /** 默认缓存 30 分钟 */
    private static final long DEFAULT_TTL = 30 * 60;

    /**
     * 获取缓存字符串
     */
    public String get(String key) {
        return redisTemplate.opsForValue().get(key);
    }

    /**
     * 写入缓存（默认 30 分钟）
     */
    public void set(String key, Object value) {
        set(key, value, DEFAULT_TTL);
    }

    /**
     * 写入缓存
     */
    public void set(String key, Object value, long ttlSeconds) {
        redisTemplate.opsForValue().set(key, JSON.toJSONString(value), ttlSeconds, TimeUnit.SECONDS);
    }

    /**
     * 删除单个缓存
     */
    public void delete(String key) {
        redisTemplate.delete(key);
    }

    /**
     * 按前缀删除所有匹配的缓存
     */
    public void deleteByPrefix(String prefix) {
        Set<String> keys = redisTemplate.keys(prefix + "*");
        if (keys != null && !keys.isEmpty()) {
            redisTemplate.delete(keys);
        }
    }
}
