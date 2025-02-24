package com.sea.whale.aop;


import com.alibaba.fastjson.JSON;
import com.sea.whale.entity.R;
import org.jetbrains.annotations.NotNull;
import org.springframework.core.MethodParameter;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseBodyAdvice;

@RestControllerAdvice(basePackages = {"com.sea.whale.controller"})
public class WhaleResponseHandler implements ResponseBodyAdvice<Object> {

    @Override
    public boolean supports(MethodParameter methodParameter, @NotNull Class<? extends HttpMessageConverter<?>> aClass) {
        return !methodParameter.getParameterType().equals(R.class) && !methodParameter.hasMethodAnnotation(NotResponseBody.class);
    }


    @Override
    public Object beforeBodyWrite(Object body, @NotNull MethodParameter methodParameter, @NotNull MediaType selectedContentType, @NotNull Class<? extends HttpMessageConverter<?>> selectedConverterType, @NotNull ServerHttpRequest request, @NotNull ServerHttpResponse response) {
        if (body instanceof R)
            return body;
        else if (body == null)
            return R.ok();
        else if (body instanceof String) {
            //解决返回值为字符串时，不能正常包装
            return JSON.toJSONString(R.ok().data("data", body));
        }
        return R.ok().data("data", body);
    }


}
