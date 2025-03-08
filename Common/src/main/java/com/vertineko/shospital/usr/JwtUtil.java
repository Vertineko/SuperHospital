package com.vertineko.shospital.usr;

import cn.hutool.core.date.DateUtil;
import cn.hutool.jwt.JWT;
import lombok.extern.slf4j.Slf4j;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * Jwt工具类
 */
@Slf4j
public class JwtUtil {

    /**
     * 密钥
     */
    private static final byte[] privateKey = "asdfghjklqwertyuiopzxcvbnm".getBytes();

    /**
     * 生成Jwt
     * @param user 传入的需要载入到Payload中的用户信息
     * @return 返回Jwt Token
     */
    public static String createToken(Map<String, String> user) {
        Date now = new Date();
        return JWT.create()
                .setPayload("id", user.get("id"))
                .setPayload("username", user.get("username"))
                .setPayload("role", user.get("role"))
                //设置签发时间
                .setIssuedAt(now)
                //设置Jwt过期时间点为签发时间4小时以后
                .setExpiresAt(DateUtil.offsetHour(now, 4))
                .setKey(privateKey)
                .sign();
    }

    /**
     * 解析Jwt Token
     * @param token 传入的Jwt Token
     * @return 返回载入到Payload中的信息
     */
    public static Map<String, String> parse(String token) {
        JWT jwt = JWT.of(token);
        Map<String, String> map = new HashMap<>();
        map.put("id" ,jwt.getPayload("id").toString());
        map.put("username", jwt.getPayload("username").toString());
        map.put("role", jwt.getPayload("role").toString());
        return map;
    }

    /**
     * 验证Jwt的密钥以及是否过期
     * @param token 传入的Jwt Token
     */
    public static boolean verify(String token){
        JWT jwt = JWT.of(token);
        return jwt.setKey(privateKey).verify() && jwt.validate(0);

    }
}
