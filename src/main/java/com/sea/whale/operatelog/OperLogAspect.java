package com.sea.whale.operatelog;

import cn.hutool.json.JSONUtil;
import com.sea.whale.config.ExecutePoolConfig;
import com.sea.whale.exception.AppException;
import jakarta.annotation.Resource;
import jakarta.servlet.http.HttpServletRequest;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.ApplicationContext;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.ParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;
import org.springframework.web.util.UriUtils;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;

@Aspect
@Component
@Slf4j
@Order(1)
public class OperLogAspect {

    @Pointcut("@annotation(com.sea.whale.operatelog.OperateLog)")
    public void logPointcut() {
    }

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private ExecutePoolConfig executePoolConfig;

    @SneakyThrows
    @Around(value = "logPointcut()")
    public Object aroundNotice(ProceedingJoinPoint joinPoint) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 获取用户名并进行安全解码
        String username = safelyDecodeHeader(request.getHeader("username"));

        // 记录方法执行时间
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long execTime = System.currentTimeMillis() - beginTime;
        log.info("Method execution time: {}ms", execTime);

        // 构建操作日志
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperateLog opLog = signature.getMethod().getAnnotation(OperateLog.class);
        OperateLogPojo logPojo = new OperateLogPojo(
                opLog,
                username,
                joinPoint.getArgs(),
                getRequestParams(request, joinPoint)
        );

        // 异步处理日志
        executePoolConfig.taskExecutor().execute(() ->
                applicationContext.getBean(opLog.handler()).handler(logPojo)
        );

        return result;
    }

    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = requestAttributes.getRequest();

        // 安全获取用户名
        String username = safelyDecodeHeader(request.getHeader("username"));

        // 构建异常日志
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        OperateLog opLog = signature.getMethod().getAnnotation(OperateLog.class);
        OperateLogPojo logPojo = new OperateLogPojo(
                opLog,
                username,
                joinPoint.getArgs(),
                getRequestParams(request, (ProceedingJoinPoint) joinPoint)
        );

        // 获取异常信息
        String errorMsg = (e instanceof AppException) ? e.getMessage() : e.toString();

        // 异步处理异常日志（仅保留异步调用）
        executePoolConfig.taskExecutor().execute(() ->
                applicationContext.getBean(opLog.handler()).failHandler(logPojo, errorMsg)
        );
    }

    /**
     * 安全解码URL编码的请求头
     */
    private String safelyDecodeHeader(String headerValue) {
        if (headerValue == null || headerValue.isEmpty()) {
            return "Anonymous";
        }

        try {
            // 使用 Spring 官方工具类进行解码
            return UriUtils.decode(headerValue, StandardCharsets.UTF_8);
        } catch (IllegalArgumentException e) {
            log.warn("Header 值解码失败: {}, 使用原始值", headerValue);
            return headerValue;
        }
    }

    /**
     * 获取请求参数（支持GET/POST）
     */
    private String getRequestParams(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        boolean isBodyRequest = "POST".equals(httpMethod)
                || "PUT".equals(httpMethod)
                || "PATCH".equals(httpMethod);

        if (isBodyRequest) {
            return JSONUtil.toJsonStr(joinPoint.getArgs());
        }

        // 使用新版参数名发现器
        ParameterNameDiscoverer discoverer = new DefaultParameterNameDiscoverer();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        String[] paramNames = discoverer.getParameterNames(signature.getMethod());
        Object[] args = joinPoint.getArgs();

        // 处理参数名与值对应关系
        if (args == null || paramNames.length != args.length) {
            return Arrays.toString(args);
        }

        StringBuilder params = new StringBuilder();
        for (int i = 0; i < args.length; i++) {
            params.append(String.format("(%s: %s)", paramNames[i], args[i]));
        }
        return params.toString();
    }
}
