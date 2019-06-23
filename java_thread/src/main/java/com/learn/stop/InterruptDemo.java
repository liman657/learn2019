package com.learn.stop;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2019/6/19
 * comment:
 *
 * Thread类中存在一个变量 isInterrupted 默认是false
 */
public class InterruptDemo {

    private static int i ;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            while(!Thread.currentThread().isInterrupted()){
                i++;
            }
            System.out.println("i:"+i);
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();//将isInterrupted设置成true
    }

}
