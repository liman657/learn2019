package com.learn.stop;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2019/6/19
 * comment:
 */
public class ExceptionThreadDemo {
    private static int i;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {

                    //TODO:中断一个处于阻塞状态的线程，会抛出异常，join/wait/notify
                    TimeUnit.SECONDS.sleep(7);//这里抛出的InterruptedException异常，会复位中断标志位
                    System.out.println("我没有停止");
                } catch (InterruptedException e) {
                    e.printStackTrace();
//                    break;
                }
            }
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();//将isInterrupted设置成true

        System.out.println(thread.isInterrupted());//这里会输出false，这里中断标志位就已复位了
    }
}