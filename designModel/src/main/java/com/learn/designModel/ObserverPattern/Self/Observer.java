package com.learn.designModel.ObserverPattern.Self;

/**
 * autor:liman
 * comment: 观察者
 */
public class Observer {

    public void advice(Event e){
        System.out.println("观察者收到了通知：Event"+e);
    }
}
