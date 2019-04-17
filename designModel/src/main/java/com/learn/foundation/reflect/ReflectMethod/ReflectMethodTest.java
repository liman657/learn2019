package com.learn.foundation.reflect.ReflectMethod;

import org.junit.Test;

import java.lang.reflect.Method;

/**
 * autor:liman
 * comment: 反射执行指定方法
 */
public class ReflectMethodTest {

    /**
     * 反射获取方法信息
     * @throws Exception
     */
    @Test
    public void testReflectMethod() throws Exception {
        Class clazz = Class.forName("com.learn.foundation.reflect.ClassInfo.Person");

        //1.获取方法
            //1.1 获取clazz中对应类中的所有方法，不能获取private方法，且获取从父类中继承的方法
        Method[] methods = clazz.getMethods();
        printMethods(methods);

        //2.获取所有方法——包括私有方法
            //所有声明的方法，都可以获取到，且只获取当前类的方法
        methods = clazz.getDeclaredMethods();
        printMethods(methods);

        //3.获取指定的方法
        //需要指定参数名称和参数列表，无参则不需要写
        Method method = clazz.getDeclaredMethod("setName",String.class);
        System.out.println(method);

        method = clazz.getDeclaredMethod("setAge",int.class);
        System.out.println(method);

        // 执行方法
        Object obj = clazz.newInstance();
        //invoke方法的第一个参数就是指定对象，表示指定某个对象的方法。剩下的参数就是传入执行该方法的参数
        method.invoke(obj,2);
    }

    private void printMethods(Method[] methods){
        for(Method method:methods){
            System.out.println(" "+method.getName());
        }
        System.out.println();
    }

}
