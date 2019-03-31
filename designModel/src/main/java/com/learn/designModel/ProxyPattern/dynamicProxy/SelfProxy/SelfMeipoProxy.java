package com.learn.designModel.ProxyPattern.dynamicProxy.SelfProxy;

import java.lang.reflect.Method;

/**
 * autor:liman
 * comment:
 */
public class SelfMeipoProxy implements SelfInvocationHandler {

    private Person target;

    public Object getInstance(Person person){
        this.target = person;
        Class<?> clazz = target.getClass();
        return SelfProxy.newProxyInstance(new SelfClassLoader(),clazz.getInterfaces(),this);
    }

    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        before();
        Object object = method.invoke(this.target, args);
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
