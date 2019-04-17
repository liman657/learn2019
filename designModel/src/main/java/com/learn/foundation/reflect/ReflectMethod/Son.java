package com.learn.foundation.reflect.ReflectMethod;

import com.learn.foundation.reflect.ClassInfo.Person;

/**
 * autor:liman
 * comment:
 */
public class Son extends Person {

    private String methodInSon(String name){
        System.out.println("子类中的私有方法，参数为：name,"+name);
        return name;
    }

}
