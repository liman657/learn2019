package com.learn.TypeOfLock.SynchronizedDemo;

/**
 * autor:liman
 * createtime:2019/6/30
 * comment:
 */
public class SyncDemo02 {

    public static void main(String[] args) {
        SetPrivateNum setPrivateNumUserA = new SetPrivateNum();
        SetPrivateNum setPrivateNumUserOther = new SetPrivateNum();

        ThreadA thread01 = new ThreadA(setPrivateNumUserA);
        ThreadB thread02 = new ThreadB(setPrivateNumUserOther);
        ThreadC thread03 = new ThreadC(setPrivateNumUserA);

        thread01.start();
//        thread02.start();
        thread03.start();

//        ThreadA thread03 = new ThreadA(setPrivateNumUserA);
//        thread03.start();
    }
}