package com.learn.designModel.ObserverPattern.Self;

/**
 * autor:liman
 * comment: 被观察者
 */
public class Subject extends EventListener{

    public void add(){
        System.out.println("系统添加相关操作");
        this.trigger("ON_ADD");
    }

    public void remove(){
        System.out.println("系统删除相关操作");
        this.trigger("ON_REMOVE");
    }

}
