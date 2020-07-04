package com.learn.thread.update.phase01.threadinterrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/7
 * comment:join的interrupt的实例
 */
@Slf4j
public class ThreadInterruptJoinDemo {

    public static void main(String[] args) {

        Thread t1 = new Thread(() -> {
//            IntStream.rangeClosed(1,89).forEach(i->log.info("t1 thread index :{}",i));
            while(true){
                if(Thread.currentThread().isInterrupted()){
                    log.error("t1 is interrupted ? {}", Thread.currentThread().isInterrupted());
                    log.error("t1 is be interrupted ");
                    break;
                }
            }
        },"t1");

        //t2线程用于打断t1线程
        Thread t2 = new Thread(()->{
            try {
                TimeUnit.SECONDS.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            t1.interrupt();
        });

        t1.start();
        t2.start();
//        TimeUnit.SECONDS.sleep(10);
        log.info("t1 is interrupted ? {}", t1.isInterrupted());
        try {
            t1.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        t1.interrupt();
//        TimeUnit.MILLISECONDS.sleep(3);
        log.info("t1 is interrupted ? {}", t1.isInterrupted());


    }
}
