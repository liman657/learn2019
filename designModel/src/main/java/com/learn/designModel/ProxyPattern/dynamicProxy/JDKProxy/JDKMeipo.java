package com.learn.designModel.ProxyPattern.dynamicProxy.JDKProxy;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

/**
 * autor:liman
 * comment:
 */
public class JDKMeipo implements InvocationHandler {

    private Object target;

    public Object getInstance(Object person){
        this.target = person;
        Class<?> clazz = target.getClass();
        //获得对目标类的代理对象
        return Proxy.newProxyInstance(clazz.getClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object object = method.invoke(this.target, args);
        System.out.println(proxy.getClass().getSimpleName());
        after();
        return object;
    }

    private void before(){
        System.out.println("搜集各种男孩的信息");
    }

    private void after(){
        System.out.println("询问结果");
    }
}
