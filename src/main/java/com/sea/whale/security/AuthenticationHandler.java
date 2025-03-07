package com.sea.whale.security;

import com.sea.whale.entity.R;
import com.sea.whale.enums.ResultEnum;
import com.sea.whale.utils.JsonUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import java.io.IOException;

/**
 * <p>
 * Spring Security认证异常拦截器
 * </p>
 *
 * @author chengyunbo
 * @since 2025-02-26 16:39
 */
@Configuration
public class AuthenticationHandler implements AuthenticationEntryPoint {

    private int determineErrorCode(AuthenticationException ex) {
        if (ex instanceof AuthenticationCredentialsNotFoundException) {
            return ResultEnum.JWT_LOSE_CODE.getCode();
        } else if (ex instanceof AuthenticationServiceException) {
            return ResultEnum.JWT_FAIL_CODE.getCode();
        }
        return 1000;
    }

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException{
        // 根据异常类型确定错误码
        int code = determineErrorCode(authException);
        String message = authException.getMessage();

        // 构建错误响应
        response.setHeader("Access-Control-Allow-Origin", request.getHeader("Origin"));
        response.setHeader("Access-Control-Allow-Credentials", "true");
        response.setHeader("Access-Control-Expose-Headers", "Authorization");
        response.setContentType("application/json;charset=UTF-8");
        response.getWriter().write(JsonUtil.toJson(R.error(code, message)));
    }
}
