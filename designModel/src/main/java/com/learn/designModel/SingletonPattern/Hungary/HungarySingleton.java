package com.learn.designModel.SingletonPattern.Hungary;

/**
 * autor:liman
 * comment:饿汉式单例
 */
public class HungarySingleton {

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

}
