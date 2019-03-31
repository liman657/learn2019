package com.learn.designModel.ProxyPattern.dynamicProxy.SelfProxy;

/**
 * autor:liman
 * comment:
 */
public class Girl implements Person{
    @Override
    public void seeBoy() {
        System.out.println("高否？");
        System.out.println("富否？");
        System.out.println("帅否？");
    }
}
