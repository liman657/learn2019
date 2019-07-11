package com.learn.lockSupportDemo;

import java.util.concurrent.locks.LockSupport;

/**
 * autor:liman
 * createtime:2019/7/7
 * comment:
 */
public class TestObjLockpark {

    public static void main(String[] args) throws InterruptedException {
        final Object obj = new Object();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i=0;i<10;i++){
                    sum+=i;
                }
                LockSupport.park();
                System.out.println(sum);
            }
        });

        threadA.start();
        Thread.sleep(1000);
        LockSupport.unpark(threadA);
    }
}