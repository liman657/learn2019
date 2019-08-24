package com.learn.springbootstarter.AutoImportSelector;

import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * autor:liman
 * createtime:2019/8/19
 * comment: 类似于EnableAutoConfiguration注解
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Inherited
@Import({SomeServiceRegistrar.class})
public @interface EnableDefineService {

    //配置一些方法
    Class<?>[] exclude() default {};

}
