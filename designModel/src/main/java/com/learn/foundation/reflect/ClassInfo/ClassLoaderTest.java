package com.learn.foundation.reflect.ClassInfo;

import org.junit.Test;

/**
 * autor:liman
 * comment: 类加载器测试
 * 类加载器是用来将class装载进JVM的，JVM规范定义了两种类型的类装载器
 * 启动类装载器（BootStrap）和用户自定义装载器（user-defined class loader)
 * JVM在运行的时候会产生3个类加载器组成的初始化加载器层次结构
 *
 * 顶层：Bootstrap Classloader  (引导类加载器，C++编写，JVM自带的类加载器，负责加载Java平台核心库）
 * 中层：Extension ClassLoader  （扩展类加载器，负责jdk home/lib/ext目录下的jar包或者-D java.ext.dirs指定目录下的jar包
 * 底层：System Classloader (系统类加载器，负责 java -classpath或-D java.class.path所指定的目录下的类与jar包载入
 *
 *
 * 自底向上检查类是否已经装载
 * 自顶向下尝试加载类
 */
public class ClassLoaderTest {

    @Test
    public void testClassLoader() throws Exception {
        //1、获取一个系统的类加载器（系统类加载器）
        ClassLoader classLoader = ClassLoader.getSystemClassLoader();
        System.out.println(classLoader);

        //2、获取类加载器的父类（扩展类加载器）
        classLoader = classLoader.getParent();
        System.out.println(classLoader);

        //3、获取扩展类加载器的父类加载器（引导类加载器，不可获取）
        classLoader = classLoader.getParent();
        System.out.println(classLoader);

        //4、测试当前类由那个类加载器进行加载
        classLoader = Class.forName("com.learn.foundation.reflect.ClassInfo.Person").getClassLoader();//系统类加载器
        System.out.println(classLoader);

        //5、测试Object是由那个类加载器加载
        classLoader = Class.forName("java.lang.Object").getClassLoader();//系统类加载器
        System.out.println(classLoader);
    }

}
