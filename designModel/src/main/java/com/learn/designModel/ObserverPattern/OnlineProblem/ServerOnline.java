package com.learn.designModel.ObserverPattern.OnlineProblem;

/**
 * autor:liman
 * comment: 服务器，被观察者
 */
public class ServerOnline extends MessageListener{

    public void runNormal(){
        System.out.println("服务器正常运行");
    }

    public void runExceptionLi(){
        System.out.println("服务器运行出现异常,需要CoderLi处理");
        System.out.println("=================开始进入告警机制================");
        this.trigger("ERRORLi");
    }

    public void runExceptionWang(){
        System.out.println("服务器运行出现异常,需要CoderWang处理");
        System.out.println("=================开始进入告警机制================");
        this.trigger("ERRORWang");
    }

}
