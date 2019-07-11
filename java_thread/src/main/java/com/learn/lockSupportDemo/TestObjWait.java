package com.learn.lockSupportDemo;

/**
 * autor:liman
 * createtime:2019/7/7
 * comment:
 */
public class TestObjWait {

    public static void main(String[] args) throws InterruptedException {
        final Object obj = new Object();
        Thread threadA = new Thread(new Runnable() {
            @Override
            public void run() {
                int sum = 0;
                for(int i =0;i<10;i++){
                    sum+=i;
                }

                try {
                    synchronized (obj){
                       obj.wait();
                    }
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println(sum);
            }
        });

        threadA.start();
        Thread.sleep(1000);
        synchronized (obj){
            obj.notify();
        }

    }
}