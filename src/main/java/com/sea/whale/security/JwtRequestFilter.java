package com.sea.whale.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 * JWT令牌验证拦截器
 * </p>
 *
 * @author chengyunbo03
 * @since 2025-02-25 18:02
 */

@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final List<RequestMatcher> excludeMatchers;

    public JwtRequestFilter(List<RequestMatcher> excludeMatchers) {
        this.excludeMatchers = excludeMatchers;
    }

    @Override
    protected boolean shouldNotFilter(@NotNull HttpServletRequest request) {
        return excludeMatchers.stream()
                .anyMatch(matcher -> matcher.matches(request));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, @NotNull HttpServletResponse response, @NotNull FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (token != null && !token.isEmpty()) {
            Claims claims = JwtUtil.parseToken(token);
            if (claims != null) {
                // 关键步骤：创建认证对象并注入上下文(缺少此步骤即使JWT令牌有效,也不能验证通过,必须注入认证对象)
                Authentication authentication = createAuthentication(claims);
                SecurityContextHolder.getContext().setAuthentication(authentication);
                log.info("JWT认证成功");
            }else {
                throw new AuthenticationServiceException("JWT令牌无效,请重新登录");
            }
        } else {
            throw new AuthenticationCredentialsNotFoundException("JWT令牌缺失,请重新登录");
        }
        filterChain.doFilter(request, response);
    }

    private Authentication createAuthentication(Claims claims) {
        // 创建认证对象
        UserDetails userDetails = ResUser.builder()
                .username(String.valueOf(claims.get("username")))
                .password("")
                .role(String.valueOf(claims.get("role")))
                .build();
        return new UsernamePasswordAuthenticationToken(
                userDetails, null, userDetails.getAuthorities());
    }
}
