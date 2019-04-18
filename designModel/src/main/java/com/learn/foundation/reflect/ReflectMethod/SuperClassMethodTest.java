package com.learn.foundation.reflect.ReflectMethod;

import org.junit.Test;
import java.lang.reflect.Method;

/**
 * autor:liman
 * comment: 反射获取父类方法
 */
public class SuperClassMethodTest {

    @Test
    public void testGetSuperClassMethod() throws Exception {
        String className = "com.learn.foundation.reflect.ReflectMethod.Son";

        Class clazz = Class.forName(className);
        Class superClazz = clazz.getSuperclass();
        System.out.println(superClazz);
    }

    @Test
    public void testVisitMethod(){
        Son son = new Son();

        invokePrivateMethod(son,"methodInSon","son");
    }

    public Object invokePrivateMethod(Object obj,String methodName,Object ... args){
        //1.获取Method对象
        Class[] parameterTypes = new Class[args.length];
        for(int i=0;i<args.length;i++){
            parameterTypes[i] = args[i].getClass();
        }

        Method method = getMethod(obj.getClass(),methodName,parameterTypes);
        method.setAccessible(true);

        try {
            //2.执行Method方法，同时返回方法的返回值
            return method.invoke(obj,args);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 获取clazz的methodName方法，从下往上找，如果没有找到会直接找到父类去
     * @param clazz
     * @param methodName
     * @param parameterType
     * @return
     */
    public Method getMethod(Class clazz, String methodName, Class ... parameterType){
        for(;clazz!=Object.class;clazz = clazz.getSuperclass()){
            try {
                Method method = clazz.getDeclaredMethod(methodName,parameterType);
                return method;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        return null;
    }

}
