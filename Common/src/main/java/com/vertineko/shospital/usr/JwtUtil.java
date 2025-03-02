package com.vertineko.shospital.usr;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.security.Keys;

import java.security.Key;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class JwtUtil {

    // 密钥（建议从配置文件中读取）
    private static final Key SECRET_KEY = Keys.secretKeyFor(SignatureAlgorithm.HS256);

    // JWT 过期时间（单位：毫秒）
    private static final long EXPIRATION_TIME = 86400000; // 24小时

    /**
     * 生成 JWT
     *
     * @param subject 主题（通常是用户ID）
     * @param claims  自定义声明
     * @return JWT 字符串
     */
    public static String generateToken(String subject, Map<String, Object> claims) {
        return Jwts.builder()
                .setClaims(claims) // 设置自定义声明
                .setSubject(subject) // 设置主题
                .setIssuedAt(new Date()) // 设置签发时间
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME)) // 设置过期时间
                .signWith(SECRET_KEY) // 使用密钥签名
                .compact(); // 生成 JWT
    }

    /**
     * 解析 JWT
     *
     * @param token JWT 字符串
     * @return 声明（Claims）
     */
    public static Claims parseToken(String token) {
        return Jwts.parser()
                .setSigningKey(SECRET_KEY) // 设置密钥
                .build()
                .parseClaimsJws(token) // 解析 JWT
                .getBody(); // 获取声明
    }

    /**
     * 验证 JWT 是否有效
     *
     * @param token JWT 字符串
     * @return 是否有效
     */
    public static boolean validateToken(String token) {
        try {
            parseToken(token); // 尝试解析 JWT
            return true; // 解析成功，JWT 有效
        } catch (Exception e) {
            return false; // 解析失败，JWT 无效
        }
    }

    /**
     * 获取 JWT 中的主题（通常是用户ID）
     *
     * @param token JWT 字符串
     * @return 主题
     */
    public static String getSubject(String token) {
        return parseToken(token).getSubject();
    }

    /**
     * 获取 JWT 中的自定义声明
     *
     * @param token JWT 字符串
     * @return 自定义声明
     */
    public static Map<String, Object> getClaims(String token) {
        return new HashMap<>(parseToken(token));
    }
}
