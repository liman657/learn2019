package com.learn.designModel.ProxyPattern.dynamicProxy.CGLIBProxy;

/**
 * autor:liman
 * comment:
 */
public class CGLibTest {

    public static void main(String[] args) throws Exception {
        Boy boy = (Boy)new CGlibMeipo().getInstance(Boy.class);
        boy.seeBoy();

    }

}
