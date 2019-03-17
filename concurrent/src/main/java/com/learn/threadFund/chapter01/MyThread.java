package com.learn.threadFund.chapter01;

/**
 * autor:liman
 * comment:
 */
public class MyThread extends Thread{

    @Override
    public void run() {
        super.run();
        System.out.println("this is self thread.");
    }
}
