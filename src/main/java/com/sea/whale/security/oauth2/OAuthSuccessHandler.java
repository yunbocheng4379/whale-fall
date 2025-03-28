package com.sea.whale.security.oauth2;

import com.sea.whale.security.JwtUtil;
import com.sea.whale.security.ResUser;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

/**
 * <p>
 * OAuth2认证成功处理器
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-26 16:54
 */
public class OAuthSuccessHandler implements AuthenticationSuccessHandler {

    @Value("${base-url}")
    private String baseUrl;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request,
                                        HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        ResUser resUser = (ResUser) authentication.getPrincipal();
        Map<String, Object> payload = new HashMap<>();
        payload.put("id", String.valueOf(resUser.getId()));
        payload.put("username", resUser.getUsername());
        payload.put("role", resUser.getRole());
        // 生成JWT
        String token = JwtUtil.getToken(payload);
        // 返回给前端
        String redirectUrl = baseUrl + "/#/oauth-callback?token=" + token + "&username=" + URLEncoder.encode(resUser.getUsername(), "UTF-8");
        response.sendRedirect(redirectUrl);
    }


}
