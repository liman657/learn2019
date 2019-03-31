package com.learn.designModel.ProxyPattern.dynamicProxy.SelfProxy;

/**
 * autor:liman
 * comment: 自己手写代理类原理实现
 */
public class SelfProxyTest {
    public static void main(String[] args) {
        Person person = (Person) new SelfMeipoProxy().getInstance(new Girl());
        person.seeBoy();
    }
}