package com.learn.springioc.framework.aop;

/**
 * autor:liman
 * createtime:2019/6/8
 * comment: Aop的顶层接口
 */
public interface SelfAopProxy {
    Object getProxy();

    Object getProxy(ClassLoader classLoader);
}