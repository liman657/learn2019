package com.learn.foundation.reflect.ReflectConstructor;

import com.learn.foundation.reflect.ClassInfo.Person;
import com.sun.corba.se.impl.ior.OldJIDLObjectKeyTemplate;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;

/**
 * autor:liman
 * comment: 反射获取构造函数
 */
public class ReflectConstructorTest {

    @Test
    public void testConstructor() throws Exception {
        String className = "com.learn.foundation.reflect.ClassInfo.Person";
        Class<Person> clazz = (Class<Person>) Class.forName(className);

        Constructor<Person> [] constructors = (Constructor<Person>[]) Class.forName(className).getConstructors();

        for(Constructor<Person> constructor:constructors){
            System.out.println(constructor);
        }

        Constructor<Person> constructor = clazz.getConstructor(String.class,int.class);
        System.out.println(constructor);

        Object obj = constructor.newInstance("liman",20);

        System.out.println(((Person) obj).getName());
    }

}
