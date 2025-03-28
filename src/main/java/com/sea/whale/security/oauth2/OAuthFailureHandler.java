package com.sea.whale.security.oauth2;

import com.sea.whale.exception.AppException;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;

import java.io.IOException;
import java.net.URLEncoder;

/**
 * <p>
 * OAuth2认证失败处理器
 * </p>
 *
 * @author chengyunbo
 * @since 2025-03-26 17:18
 */
public class OAuthFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        throw new AppException("验证失败");
    }
}
