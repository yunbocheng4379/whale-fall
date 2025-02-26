package com.sea.whale.utils;

import cn.hutool.core.exceptions.ExceptionUtil;
import com.baomidou.mybatisplus.annotation.TableId;
import com.sea.whale.operatelog.LogProperty;
import lombok.extern.slf4j.Slf4j;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * @author chengyunbo
 * @since 2024-02-23
 * @description:  对象比较器
 */

@Slf4j
public class LogUtil {

    public static String compareObject(Object oldBean, Object  newBean) {
        StringBuilder str = new StringBuilder();
        try {
            if (oldBean.getClass() == newBean.getClass()) {// 只有两个对象都是同一类型的才有可比性
                Class<? extends Object> clazz = oldBean.getClass();
                Field[] fields = oldBean.getClass().getDeclaredFields();
                int i = 1;
                for (Field field : fields) {
                    if ("serialVersionUID".equals(field.getName())) {
                        continue;
                    }
                    if (i==1){
                        TableId annotation = field.getAnnotation(TableId.class);
                        PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);

                        Method getMethod = pd.getReadMethod();
                        Object o1 = getMethod.invoke(oldBean);
                        Object o2 = getMethod.invoke(newBean);
                        if (annotation != null && o1.toString().equals(o2.toString())){
                            str.append(field.getName()).append("：").append(o1).append(">");
                        }
                    }

                    if (field.getAnnotation(LogProperty.class) == null){
                        continue;
                    }
                    PropertyDescriptor pd = new PropertyDescriptor(field.getName(), clazz);

                    Method getMethod = pd.getReadMethod();
                    Object o1 = getMethod.invoke(oldBean);
                    Object o2 = getMethod.invoke(newBean);
                    if (o1 == null && o2 == null) {
                        continue;
                    }
                    String s1 = o1 == null ? "" : o1.toString();//避免空指针异常
                    String s2 = o2 == null ? "" : o2.toString();//避免空指针异常
                    if (!s1.equals(s2)) {
                        if (i != 1) {
                            str.append(";");
                        }
                        // 获取注解值
                        String fieldName = field.getAnnotation(LogProperty.class).value();
                        str.append(fieldName).append("：由【").append(s1).append("】改为【").append(s2).append("】");
                        i++;
                    }
                }
            }
        } catch (Exception e) {
            log.error("解析操作参数信息异常，异常信息{}", ExceptionUtil.stacktraceToString(e));
        }
        return str.toString();
    }

    public static String describeObject(Object bean){
        StringBuilder str = new StringBuilder();
        try {
            Field[] fields = bean.getClass().getDeclaredFields();
            for (Field field : fields) {
                if (field.getAnnotation(LogProperty.class) == null){
                    continue;
                }
                field.setAccessible(true);
                String fieldName = field.getAnnotation(LogProperty.class).value();
                Object fieldValue = field.get(bean);
                if (fieldValue == null){
                    continue;
                }
                str.append(fieldName).append("：").append(fieldValue).append("；");
            }
        } catch (Exception e) {
            log.error("解析操作参数信息异常，异常信息{}", ExceptionUtil.stacktraceToString(e));
        }
        return str.toString();
    }
}
