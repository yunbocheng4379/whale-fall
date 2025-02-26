package com.sea.whale.security;


import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import lombok.extern.slf4j.Slf4j;

import javax.crypto.SecretKey;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * 使用指定的密钥（key）生成JWT令牌和解析JWT
 * 验证JWT的签名是否正确。
 * 检查JWT是否过期或被篡改。
 * </p>
 *
 * @author chengyunbo
 * @since 2025-02-25 13:25
 */

@Slf4j
public class JwtUtil {

    //密钥
    private static final String secretKeyString = "3f2e1d4c5b6a79808f7e6d5c4b3a29181716151413121110";
    static SecretKey key = Keys.hmacShaKeyFor(Decoders.BASE64.decode(secretKeyString));

    //令牌过期时间
    private static final long EXPIRE_TIME = 24 * 60 * 60 * 1000;

    /**
     * 生成JWT令牌
     */
    public static String getToken(Map<String, Object> payload) {
        Map<String, Object> headers = new HashMap<>();
        headers.put("alg", "HS256");
        headers.put("typ", "JWT");
        payload.put("exp", System.currentTimeMillis() + EXPIRE_TIME);

        //生成令牌
        return Jwts.builder()
                .setHeaderParams(headers)
                .setClaims(payload)
                .signWith(key)
                .compact();
    }

    /**
     * 解析JWT令牌
     */
    public static Claims parseToken(String token) {
        Claims claims = Jwts.parser()
                .setSigningKey(key)
                .parseClaimsJws(token)
                .getBody();
        Long expiredTime = (Long) claims.get("exp");
        Long secondTime = System.currentTimeMillis();
        return expiredTime >= secondTime ? claims : null;
    }

}
