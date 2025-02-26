package com.sea.whale.security;

import com.sea.whale.entity.R;
import com.sea.whale.enums.ResultEnum;
import com.sea.whale.utils.JsonUtil;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
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
    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException {
            // 根据异常类型确定错误码
            int code = determineErrorCode(authException);
            String message = authException.getMessage();

            // 构建响应
            response.setStatus(HttpStatus.UNAUTHORIZED.value());
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(JsonUtil.toJson(
                    R.error(code, message)
            ));
    }

    private int determineErrorCode(AuthenticationException ex) {
        if (ex instanceof AuthenticationCredentialsNotFoundException) {
            return ResultEnum.JWT_LOSE_CODE.getCode();
        } else if (ex instanceof AuthenticationServiceException) {
            return ResultEnum.JWT_FAIL_CODE.getCode();
        }
        return 1000;
    }
}
