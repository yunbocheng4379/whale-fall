package com.sea.whale.operatelog;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

@Target(ElementType.METHOD)  //该注解可以作用于类型元素上：类、方法、字段
@Retention(RetentionPolicy.RUNTIME) //运行时生效
@Documented
@Component
public @interface OperateLog {

    String logResource() default ""; // 操作节点来源
    String operateName() default ""; // 操作节点类型

    int operType() default 0; // 操作类型(0：新增，1：修改，2：删除)
    Class<? extends LogInfoHandler> handler() default DefaultLogInfoHandler.class;

}
