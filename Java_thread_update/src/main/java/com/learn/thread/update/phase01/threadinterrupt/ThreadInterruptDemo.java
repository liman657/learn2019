package com.learn.thread.update.phase01.threadinterrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/7
 * comment:interrupt方法
 */
@Slf4j
public class ThreadInterruptDemo {

    public static void main(String[] args) throws InterruptedException {
//        Thread t1 = new Thread(()->{
//            while(true) {
////                log.info("this is t1 thread");
////                if(Thread.currentThread().isInterrupted()){
////                    log.info("interrupted");
////                    break;
////                }
//            }
//        },"t1");
//
//        t1.start();
//        Thread.sleep(10l);
//        log.info("t1 is interrupt : {}",t1.isInterrupted());
//        t1.interrupt();
//        log.info("t1 is interrupt : {}",t1.isInterrupted());

//        Thread t2 = new Thread(()->{
//            try {
//                TimeUnit.MINUTES.sleep(1);
//            } catch (InterruptedException e) {
//                //这一行输入为false，sleep方法清空了t2线程的中断标志位
//                log.info("t2 is interrupted ? {}",Thread.currentThread().isInterrupted());
//                log.error("interrupted");
//            }
//        },"t2");

        Thread t2 = new Thread(){
            @Override
            public void run() {
                try {
                    TimeUnit.MINUTES.sleep(1);
                } catch (InterruptedException e) {
                    //这一行输入为false，sleep方法清空了t2线程的中断标志位
                    log.info("t2 is interrupted ? {}", this.isInterrupted());
                    log.error("interrupted");
                }
            }
        };


        t2.start();
        TimeUnit.SECONDS.sleep(2);
        log.info("t1 is interrupt : {}", t2.isInterrupted());
        t2.interrupt();
        //预留3毫秒给t2调用sleep清空标志位，不然影响判断结果
        TimeUnit.MILLISECONDS.sleep(3);
        log.info("t1 is interrupt : {}", t2.isInterrupted());
    }
}
