package com.learn.designModel.ProxyPattern.dynamicProxy.SelfProxy;

import java.lang.reflect.Method;

/**
 * autor:liman
 * comment:
 */
public interface SelfInvocationHandler {

    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable;

}
