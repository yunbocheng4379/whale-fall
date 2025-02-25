package com.sea.whale.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.extern.slf4j.Slf4j;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 提供Jwt工具类
 * 提供Token生成和验证方法
 * </p>
 *
 * @author chengyunbo03@gyyx.cn
 * @since 2025-02-25 13:25
 */

@Slf4j
public class JwtUtil {

    // 密钥
    private static final String key = "3f2e1d4c5b6a79808f7e6d5c4b3a29181716151413121110";

    // 令牌过期时间                           分  秒  毫秒
    private static final long EXPIRE_TIME = 60 * 60 * 1000;

    /**
     * 生成JWT令牌
     */
    public static String getToken(Map<String, Object> payload) {
        //设置头部信息
        Map<String, Object> headers = new HashMap<>();
        //设置签名算法
        headers.put("alg", "HS256");
        //设置令牌类型
        headers.put("typ", "JWT");
        //系统当前时间（毫秒）
        Long secondTime = System.currentTimeMillis() + EXPIRE_TIME;
        //设置过期时间
        //方案一 将过期时间放在负载中
        payload.put("exp", secondTime);

        //生成令牌
        String jwt = Jwts.builder()
                //设置头信息
                .setHeaderParams(headers)
                //设置负载信息
                .setClaims(payload)
                //使用HS256算法和密钥对JWT进行签名
                .signWith(SignatureAlgorithm.HS256, key)
                //将之前设置的头部信息、负载信息和签名信息组合成一个完整的JWT，并以字符串形式返回
                .compact();
        return jwt;
    }

    /**
     * 解析JWT令牌
     */
    public static Boolean parseToken(String token) {
        try {
            /*
                以下代码的作用：
                使用指定的密钥（key）来解析JWT。
                验证JWT的签名是否正确。
                检查JWT是否过期或被篡改。
             */
            Claims claims = Jwts.parser()
                    .setSigningKey(key)
                    .parseClaimsJws(token)
                    .getBody();
            //获取过期时间
            Long expiredTime = (Long) claims.get("exp");
            //获取当前时间戳
            Long secondTime = System.currentTimeMillis();
            return expiredTime >= secondTime;
        } catch (Exception e) {
            log.error("解析令牌失败！");
            return false;
        }
    }

}
