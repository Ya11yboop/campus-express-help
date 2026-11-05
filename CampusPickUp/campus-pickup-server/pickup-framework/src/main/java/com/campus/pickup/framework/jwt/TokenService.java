package com.campus.pickup.framework.jwt;

import com.alibaba.fastjson2.JSON;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**
 * 令牌服务：生成、校验、注销 JWT，并借助 Redis 存储登录用户
 *
 * @author campus-pickup
 */
@Component
public class TokenService {

    /** Redis 中登录令牌 key 前缀 */
    private static final String LOGIN_TOKEN_KEY = "login_tokens:";

    /** 令牌有效期（秒），默认 30 分钟 */
    private static final long EXPIRE_TIME = 30 * 60L;

    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 创建令牌
     */
    public String createToken(LoginUser loginUser) {
        String uuid = UUID.randomUUID().toString().replace("-", "");
        loginUser.setUuid(uuid);
        loginUser.setLoginTime(System.currentTimeMillis());
        loginUser.setExpireTime(System.currentTimeMillis() + EXPIRE_TIME * 1000);

        // 登录用户写入 Redis
        redisTemplate.opsForValue().set(LOGIN_TOKEN_KEY + uuid,
                JSON.toJSONString(loginUser), EXPIRE_TIME, TimeUnit.SECONDS);

        // 生成 JWT
        Map<String, Object> claims = new HashMap<>();
        claims.put("login_user_key", uuid);
        return JwtUtil.createToken(claims);
    }

    /**
     * 根据令牌获取登录用户
     */
    public LoginUser getLoginUser(String token) {
        try {
            Claims claims = JwtUtil.parseToken(token);
            String uuid = (String) claims.get("login_user_key");
            String json = redisTemplate.opsForValue().get(LOGIN_TOKEN_KEY + uuid);
            if (json == null) {
                return null;
            }
            return JSON.parseObject(json, LoginUser.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 删除登录用户（注销）
     */
    public void delLoginUser(String token) {
        try {
            Claims claims = JwtUtil.parseToken(token);
            String uuid = (String) claims.get("login_user_key");
            redisTemplate.delete(LOGIN_TOKEN_KEY + uuid);
        } catch (Exception ignored) {
        }
    }
}
