package com.learn.spring_aop.AspectJ.annotation;

import org.aspectj.lang.annotation.Pointcut;

/**
 * author:liman
 * createtime:2019/6/4
 * comment:spring中建议如此使用
 */
public class SystemArchitecture {

    @Pointcut("within(com.learn.spring_aop.AspectJ.web.*.*)")
    public void inWebLayer(){}

    @Pointcut("within(com.learn.spring_aop.AspectJ.service.*.*)")
    public void inServiceLayer(){}

    @Pointcut("within(com.learn.spring_aop.AspectJ.dao.*.*)")
    public void inDaoLayer(){}

    // service 实现，注意这里指的是方法实现，其实通常也可以使用 bean(*ServiceImpl)
    @Pointcut("execution(* com.learn..service.*.*(..))")
    public void businessService() {}

    // dao 实现
    @Pointcut("execution(* com.learn..dao.*.*(..))")
    public void dataAccessOperation() {}

}
