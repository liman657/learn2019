package com.learn.stop;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2019/6/19
 * comment: 线程的复位
 * <p>
 * Thread.interrupted();
 * InterruptedException 这两者能让线程的中断标记复位。
 */
public class ThreadResetDemo {

    private static int i;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (true) {
                try {
                    if (Thread.currentThread().isInterrupted()) {
                        System.out.println("before:" + Thread.currentThread().isInterrupted());
//                    Thread.sleep(100);
                        Thread.interrupted();//这里会将线程标志位复位
                        System.out.println("after:" + Thread.currentThread().isInterrupted());
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });

        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();//将isInterrupted设置成true
    }

}
