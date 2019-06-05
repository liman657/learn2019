package com.learn.spring_aop.AspectJ.annotation;

import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;

/**
 * author:liman
 * createtime:2019/6/4
 * comment:
 */
@Aspect
public class AdviceExample {

    @Before("com.learn.spring_aop.AspectJ.annotation.SystemArchitecture.dataAccessOperation()")
    public void doAccessCheck(){
        //TODO:实现代码
    }

//    @Before("execution(* com.")

}
