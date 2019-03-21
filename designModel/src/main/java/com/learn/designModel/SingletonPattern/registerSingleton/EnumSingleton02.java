package com.learn.designModel.SingletonPattern.registerSingleton;

/**
 * autor:liman
 * comment:
 */
public enum EnumSingleton02 {

    INSTANCE;

    private EnumSingleton02(){}

    //这个对象始终都是单例的
    private Object data;

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public static EnumSingleton02 getInstance(){return INSTANCE;}
}
