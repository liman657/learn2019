package com.learn.designModel.ObserverPattern.OnlineProblem;

/**
 * autor:liman
 * comment: 程序员小李，观察者
 */
public class CoderLi {

    public void problemMessage(Message message){
        System.out.println("程序出现了线上问题，请及时处理");
        System.out.println("异常信息为："+message);
    }
}