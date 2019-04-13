package com.learn.designModel.ObserverPattern.OnlineProblem;

/**
 * autor:liman
 * comment:
 */
public class CoderWang implements ICoder{
    @Override
    public void problemMessage(Message message) {
        System.out.println("程序出现了线上问题，CoderWang请及时处理");
        System.out.println("异常信息为："+message);
    }
}
