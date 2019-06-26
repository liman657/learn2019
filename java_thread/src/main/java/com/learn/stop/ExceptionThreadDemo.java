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
                    System.out.println("线程内部判断运行状态:"+Thread.currentThread().isInterrupted());

                    System.out.println("线程内部第一次调用："+Thread.currentThread().interrupted());
                    System.out.println("线程内部第二次调用："+Thread.currentThread().interrupted());
                    //TODO:中断一个处于阻塞状态的线程，会抛出异常，join/wait/notify
//                    TimeUnit.SECONDS.sleep(7);//这里抛出的InterruptedException异常，会复位中断标志位
                    System.out.println("我没有停止");
                } catch (Exception e) {
                    e.printStackTrace();
//                    break;
                }
            }

            System.out.println("线程内部第一次调用："+Thread.currentThread().interrupted());
            System.out.println("线程内部第二次调用："+Thread.currentThread().interrupted());
        });
        thread.start();

        TimeUnit.SECONDS.sleep(1);
        thread.interrupt();//将isInterrupted设置成true

//        Thread.interrupted();

        System.out.println(thread.isInterrupted());//这里会输出false，这里中断标志位就已复位了
    }
}