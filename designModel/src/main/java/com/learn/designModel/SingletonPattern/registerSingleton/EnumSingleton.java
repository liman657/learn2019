package com.learn.designModel.SingletonPattern.registerSingleton;

import java.awt.*;
import java.io.Serializable;

/**
 * autor:liman
 * comment:枚举式单例
 */
public class EnumSingleton implements Serializable {

    private EnumSingleton(){}

    public static EnumSingleton getInstance(){
        return Singleton.INSTANCE.getInstance();
    }

    private enum Singleton {

        INSTANCE;
        private EnumSingleton data;

        private Singleton() {
            data = new EnumSingleton();
        }

        public EnumSingleton getInstance() {
            return data;
        }
    }

//    INSTANCE;
//
//    private EnumSingleton(){
//
//    }
}

