package com.learn.foundation.reflect.ClassInfo;


import org.junit.Test;

import java.lang.reflect.Field;

/**
 * autor:liman
 * comment:
 */
public class ReflectTest {

    @Test
    public void testClass(){
        Class clazz = null;
        clazz = Person.class;
        Field[] fields = clazz.getDeclaredFields();
        System.out.println();
    }

    /**
     * 获取Class对象的三种方式
     */
    @Test
    public void testClassInfo() throws Exception{
        Class clazz = null;
        //1.通过类名获取
        clazz=Person.class;
        System.out.println(clazz);
        //2.通过对象名获取
        Object person = new Person();
        clazz = person.getClass();
        System.out.println(clazz);
        //3.通过全类名
        String className = "com.learn.foundation.reflect.ClassInfo.Person";
        clazz = Class.forName(className);
        System.out.println(clazz);
        /**
         * 字符串的实例
         */
        clazz = String.class;
        System.out.println(clazz);
        clazz = "test".getClass();
        System.out.println(clazz);
        clazz = Class.forName("java.lang.String");
        System.out.println(clazz);
        System.out.println();

        //newInstance
        clazz = Class.forName("com.learn.foundation.reflect.ClassInfo.Person");
        Object object = clazz.newInstance();//这里调用类的无参构造函数，如果没有，这里会报错
        System.out.println(object);
    }
}