package com.learn.stop;

/**
 * autor:liman
 * createtime:2019/6/26
 * comment:
 */
public class ReturnInterruptDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
           while(true){
               System.out.println("线程在运行中......");
               if(Thread.currentThread().isInterrupted()){
                   System.out.println("线程停止！！");
                   return;
               }
           }
        });
        thread.start();
        Thread.sleep(1);
        thread.interrupt();
    }
}
