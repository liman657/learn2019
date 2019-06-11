package com.learn.FactoryBean;

/**
 * autor:liman
 * createtime:2019/6/5
 * comment:这个类没有构造方法,构造方法私有化了，但是spring工厂是根据构造方法来实例化对象的
 * 这里就需要我们在配置的时候指定初始化对象的方法
 */
public class Stage {
    private static Stage instance;

    private Stage(){}

    public static Stage getInstance(){
        if(instance == null){
            instance = new Stage();
        }
        return instance;
    }

    public void CreateStageSuccess(){
        System.out.println("舞台搭建完成，舞者请上台");
    }
}
