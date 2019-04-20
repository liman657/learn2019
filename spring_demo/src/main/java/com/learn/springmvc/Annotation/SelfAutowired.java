package com.learn.springmvc.Annotation;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelfAutowired {
    String value() default "";
}
