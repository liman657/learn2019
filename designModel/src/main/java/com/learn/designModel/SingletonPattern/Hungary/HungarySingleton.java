package com.learn.designModel.SingletonPattern.Hungary;

import java.io.Serializable;

/**
 * autor:liman
 * comment:饿汉式单例
 */
public class HungarySingleton implements Serializable {

    //final是避免被反射改掉
    //private static final HungarySingleton hungarySingeton = new HungarySingleton();

    private static final HungarySingleton hungarySingeton;

    static{
        hungarySingeton = new HungarySingleton();
    }

    private HungarySingleton(){}

    public static HungarySingleton getInstance(){
        return hungarySingeton;
    }

    //重写readResolve方法，只不过是覆盖了反序列化出来的对象
    //但还是创建了两次，不过这个创建是发生在JVM层面，之前通过反序列化出来的对象会被JVM回收
    public Object readResolve(){
        return hungarySingeton;
    }

}
