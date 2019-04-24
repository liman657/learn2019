package com.learn.springmvc.Annotation;

import java.lang.annotation.*;

@Target({ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SelfRequestParam {
    String value() default "";
}