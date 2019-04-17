package com.learn.foundation.reflect.ReflectMethod;

import com.learn.foundation.reflect.ClassInfo.Person;
import org.junit.Test;

import java.lang.reflect.Method;

/**
 * autor:liman
 * comment:
 */
public class InvokeMethodTest {

    @Test
    public void testInvoke() throws Exception {

        Object obj = new Person();
        //把对象和方法名作为参数执行指定方法
        invoke(obj,"test","liman",18);


    }

    /**
     * 根据对象，方法名和参数调用指定的方法
     * @param obj
     * @param methodName
     * @param args
     * @return
     * @throws Exception
     */
    public Object invoke(Object obj,String methodName,Object ... args) throws Exception {
        //获取method对象
        Class[] parameterTypes = new Class[args.length];
        for(int i=0;i<args.length;i++){
            parameterTypes[i] = args[i].getClass();
            System.out.println(parameterTypes[i]);
        }
        Method method = obj.getClass().getDeclaredMethod(methodName,parameterTypes);
        return method.invoke(obj,args);
    }

    @Test
    public void testInvokeByClassName(){
        invoke("com.learn.foundation.reflect.ClassInfo.Person","test","liman",18);
    }

    /**
     * 全类名和方法名作为参数，调用指定的方法
     * @param className
     * @param methodName
     * @param args
     * @return
     */
    public Object invoke(String className,String methodName,Object ... args){
        Object obj = null;

        try {
            obj=Class.forName(className).newInstance();
            //调用指定的方法
            return invoke(obj,methodName,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
