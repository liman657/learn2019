package com.learn.designModel.SingletonPattern.LazyInnerClassSingleton;

import java.lang.reflect.Constructor;

/**
 * autor:liman
 * comment: 反射破坏单例
 */
public class ReflectBrokenSingleton {
    public static void main(String[] args) {
        Class<?> clazz = LazyInnerClassSingleton.class;
        try {
            //反射获取构造方法
            Constructor<?> constructor = clazz.getDeclaredConstructor();
            //将构造方法的私有属性设置为true
            constructor.setAccessible(true);
            LazyInnerClassSingleton object = (LazyInnerClassSingleton)constructor.newInstance();

            //正常渠道获取对象
            LazyInnerClassSingleton lazyInnerClassSingleton = LazyInnerClassSingleton.getInstance();

            System.out.println(object);
            System.out.println(lazyInnerClassSingleton);

            System.out.println(object == lazyInnerClassSingleton);//false
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}