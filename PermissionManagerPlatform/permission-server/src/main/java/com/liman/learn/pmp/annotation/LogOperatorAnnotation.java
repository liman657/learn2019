package com.liman.learn.pmp.annotation;

import java.lang.annotation.*;

/**
 * autor:liman
 * createtime:2021/2/12
 * comment: 插入日志的标记注解，有两个作用，一个是标记作用，一个是指明操作名称
 */
@Target(ElementType.METHOD)//只能用于方法上
@Retention(RetentionPolicy.RUNTIME)//运行时有效
@Documented
public @interface LogOperatorAnnotation {

    String value() default "";

}
