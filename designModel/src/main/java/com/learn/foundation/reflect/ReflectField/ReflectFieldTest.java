package com.learn.foundation.reflect.ReflectField;

import com.learn.foundation.reflect.ClassInfo.Person;
import org.junit.Test;

import java.lang.reflect.Field;

/**
 * autor:liman
 * comment:
 */
public class ReflectFieldTest {

    @Test
    public void testField() throws Exception {
        String className = "com.learn.foundation.reflect.ClassInfo.Person";
        Class clazz = Class.forName(className);

        //1.获取字段
            //1.1 获取所有的字段，包括公有和私有，但不能获取父类字段
        Field[] fields = clazz.getDeclaredFields();
        for(Field field:fields){
            System.out.println(" "+field.getName());
        }
        System.out.println();

        //2获取指定字段
        Field field = clazz.getDeclaredField("name");
        System.out.println(field.getName());

        //3.使用字段，如果字段是私有的，在读取前需要设置accessable为true;
        field = clazz.getDeclaredField("name");
        Person person = new Person("liman",18);
        field.setAccessible(true);
        Object val = field.get(person);
        System.out.println(val);

        //4.公有的属性，不需要设置accessable属性
        field = clazz.getDeclaredField("testProperty");
        field.set(person,"test");
        System.out.println(field.get(person));

    }

    /**
     * 获取父类的属性
     */
    @Test
    public void testGetSuperFiled() throws Exception {

        String className = "com.learn.foundation.reflect.ClassInfo.Person";
        String fieldName = "age";
        Object val = 20;

        //1.获取指定的类类型
        Class clazz = Class.forName(className);
        //2.获取指定的字段
        Field field = getField(clazz,fieldName);
        //3.新建对象
        Object obj = clazz.newInstance();
        //设置指定对象字段的值
        setFieldValue(obj,field,val);
        //获取值
        Object value = getFieldValue(obj,field);
        System.out.println(value);
    }

    private Object getFieldValue(Object obj,Field field) throws Exception {
        field.setAccessible(true);
        return field.get(obj);
    }

    private void setFieldValue(Object obj,Field field,Object val) throws Exception {
        field.setAccessible(true);
        field.set(obj,val);
    }

    private Field getField(Class clazz,String fieldName) throws Exception {
        Field field = null;
        for(Class clazz2 = clazz;clazz2!=Object.class;clazz2 = clazz2.getSuperclass()){
            field = clazz2.getDeclaredField(fieldName);
        }
        return field;
    }
}