package com.sea.whale.aop;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.sea.whale.entity.R;
import com.sea.whale.enums.ResultEnum;
import com.sea.whale.exception.AppException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author chengyunbo
 * @since 2023-03-28
 */
@RestControllerAdvice
@Slf4j
public class ExceptionAdvice {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public R exceptionHandle(MethodArgumentNotValidException e) {
        log.error(ExceptionUtil.stacktraceToString(e));
        return R.error(e.getMessage());
    }

    @ExceptionHandler(AppException.class)
    public R exceptionHandle(AppException e) {
        log.error(ExceptionUtil.stacktraceToString(e));
        return R.error(e.getCode(), e.getMessage());
    }

    @ExceptionHandler(Exception.class)
    public R exceptionHandle(Exception e) {
        log.error(ExceptionUtil.stacktraceToString(e));
        return R.error("服务器错误");
    }

    @ExceptionHandler(BadCredentialsException.class)
    public R handleBadCredentialsException(BadCredentialsException e) {
        log.error("用户名或密码输入错误，登录失败：{}", e.getMessage());
        return R.error(ResultEnum.USER_NOT_EXIST);
    }

}
