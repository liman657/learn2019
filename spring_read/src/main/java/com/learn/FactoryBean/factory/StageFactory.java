package com.learn.FactoryBean.factory;

/**
 * autor:liman
 * createtime:2019/6/5
 * comment:
 */
public class StageFactory {

    private static Stage instance;
    public static Stage getInstance(){
        if(instance == null){
            instance = new Stage();
        }
        return instance;
    }
}