package com.learn.notifyAndWait;

/**
 * autor:liman
 * createtime:2019/7/1
 * comment:
 */
public class NotifyAndWaitTest {

    public static void main(String[] args) throws InterruptedException {
        Object lock = new Object();
        Thread waitThread = new Thread(new WaitDemo(lock));
        Thread notifyThread = new Thread(new NotifyDemo(lock));

        waitThread.setName("wait thread");
        notifyThread.setName("notify thread");

        waitThread.start();
        Thread.sleep(2000);
        notifyThread.start();
    }

}
