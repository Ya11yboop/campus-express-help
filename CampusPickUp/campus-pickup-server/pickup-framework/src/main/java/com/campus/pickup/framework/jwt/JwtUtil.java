package com.campus.pickup.framework.jwt;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.Map;

/**
 * JWT 工具类
 *
 * @author campus-pickup
 */
public class JwtUtil {

    /** 签名密钥（HS256 需至少 256 位） */
    private static final String SECRET = "campus-pickup-jwt-secret-key-2026-campus-pickup-jwt-secret-key";

    /** 令牌有效期（毫秒），默认 30 分钟 */
    private static final long EXPIRE = 30 * 60 * 1000L;

    /**
     * 生成令牌
     */
    public static String createToken(Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                .signWith(SignatureAlgorithm.HS256, SECRET.getBytes(StandardCharsets.UTF_8))
                .compact();
    }

    /**
     * 解析令牌
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET.getBytes(StandardCharsets.UTF_8))
                .parseClaimsJws(token)
                .getBody();
    }
}
