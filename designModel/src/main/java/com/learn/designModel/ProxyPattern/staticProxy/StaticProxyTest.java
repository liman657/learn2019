package com.learn.designModel.ProxyPattern.staticProxy;

/**
 * autor:liman
 * comment:
 */
public class StaticProxyTest {

    public static void main(String[] args) {
        Father father = new Father(new Son());
        father.findLove();
    }

}
