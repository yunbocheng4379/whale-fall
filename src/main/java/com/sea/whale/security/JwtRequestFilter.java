package com.sea.whale.security;

import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * <p>
 *
 * </p>
 *
 * @author chengyunbo03@gyyx.cn
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
        if (validateToken(token)) {
            Claims claims = JwtUtil.parseToken(token);
            log.info(String.valueOf(claims));
        } else {
            // 如果token无效，可以抛出异常或者设置响应状态码
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }

    private boolean validateToken(String token) {
        return token != null;
    }

}
