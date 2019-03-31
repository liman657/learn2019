package com.learn.designModel.ProxyPattern.dynamicProxy.CGLIBProxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * autor:liman
 * mobilNo:15528212893
 * mail:657271181@qq.com
 * comment:
 */
public class CGlibMeipo implements MethodInterceptor {

    public Object getInstance(Class<?> clazz) throws Exception{
        //相当于Proxy，生成指定的代码
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(clazz);
        enhancer.setCallback(this);
        return enhancer.create();
    }

    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        before();
        Object object = methodProxy.invokeSuper(o,objects);
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
