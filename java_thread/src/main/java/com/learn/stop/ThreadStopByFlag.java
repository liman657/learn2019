package com.learn.stop;

/**
 * autor:liman
 * createtime:2019/6/26
 * comment:
 */
public class ThreadStopByFlag {

    private static volatile boolean isStop = false;

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
            while(!isStop){
                System.out.println("线程运行中......");
            }
            System.out.println("线程停止!!");
        });
        thread.start();
        Thread.sleep(1);
        isStop = true;
    }

}
