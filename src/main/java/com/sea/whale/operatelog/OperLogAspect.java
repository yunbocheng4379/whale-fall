package com.sea.whale.operatelog;

import cn.hutool.json.JSONUtil;
import com.sea.whale.exception.AppException;
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
import org.springframework.core.LocalVariableTableParameterNameDiscoverer;
import org.springframework.core.annotation.Order;
import org.springframework.core.task.TaskExecutor;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.io.UnsupportedEncodingException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

/**
 * @author chengyunbo@gyyx.cn
 * @since 2024-02-23
 */
@Aspect
@Component
@Slf4j
@Order(1)
public class OperLogAspect {

    @Pointcut("@annotation(com.sea.whale.operatelog.OperateLog)")
    public void logPointcut(){}

    @Resource
    private ApplicationContext applicationContext;

    @Resource
    private TaskExecutor taskExecutor;


    @SneakyThrows
    @Around(value = "logPointcut()")
    public Object AroundNotice(ProceedingJoinPoint joinPoint) {
        log.error(joinPoint.toString());
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        MethodSignature signature = (MethodSignature) joinPoint.getSignature();
        Method method = signature.getMethod();
        OperateLog opLog = method.getAnnotation(OperateLog.class);
        long beginTime = System.currentTimeMillis();
        Object result = joinPoint.proceed();
        long exeLength = System.currentTimeMillis() - beginTime;
        log.info("execution time：{}ms", exeLength);
        log.info("============= end =============");
        String username = request.getHeader("username");
        username = "程云博";
        if (!username.isEmpty()){
            username = URLDecoder.decode((username), StandardCharsets.UTF_8);
        }
        OperateLogPojo operateLogPojo = new OperateLogPojo(opLog, username, joinPoint.getArgs());
        taskExecutor.execute(()->applicationContext.getBean(opLog.handler()).handler(operateLogPojo));
        return result;
    }
    private String getRequestParams(HttpServletRequest request, ProceedingJoinPoint joinPoint) {
        String httpMethod = request.getMethod();
        StringBuilder params = new StringBuilder();
        boolean isStatus = "POST".equals(httpMethod) || "PUT".equals(httpMethod) || "PATCH".equals(httpMethod);
        if (isStatus){
            Object[] paramsArray = joinPoint.getArgs();
            params.append(JSONUtil.toJsonStr(paramsArray));
        }else {
            MethodSignature signature = (MethodSignature) joinPoint.getSignature();
            Method method = signature.getMethod();
            // 请求的方法参数值
            Object[] args = joinPoint.getArgs();
            // 请求的方法参数名称
            LocalVariableTableParameterNameDiscoverer u = new LocalVariableTableParameterNameDiscoverer();
            String[] paramNames = u.getParameterNames(method);
            if (args != null && paramNames != null) {
                for (int i = 0; i < args.length; i++) {
                    params.append("(").append(paramNames[i]).append(":").append(args[i]).append(")");
                }
            }
        }

        return params.toString();
    }
    @AfterThrowing(pointcut = "logPointcut()", throwing = "e")
    public void doAfterThrowing(JoinPoint joinPoint, Throwable e) throws NullPointerException, UnsupportedEncodingException {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        assert requestAttributes != null;
        HttpServletRequest request = requestAttributes.getRequest();
        String username = request.getHeader("username");
        username = "程云博";
        if (!username.isEmpty()){
            username = URLDecoder.decode((username), StandardCharsets.UTF_8);
        }
        OperateLog opLog = ((MethodSignature) joinPoint.getSignature()).getMethod().getAnnotation(OperateLog.class);
        OperateLogPojo operateLogPojo = new OperateLogPojo(opLog, username, joinPoint.getArgs());
        String exceptionMessage = "";
        if (e instanceof AppException) {
            exceptionMessage = e.getMessage();
        }else{
            exceptionMessage = e.toString();
        }
        String finalExceptionMessage = exceptionMessage;
        taskExecutor.execute(()->applicationContext.getBean(opLog.handler()).failHandler(operateLogPojo, finalExceptionMessage));
        applicationContext.getBean(opLog.handler()).failHandler(operateLogPojo, finalExceptionMessage);

    }
}
