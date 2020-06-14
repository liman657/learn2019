package com.learn.thread.update.threadinterrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/7
 * comment:join的interrupt的实例
 */
@Slf4j
public class ThreadInterruptWaitDemo {

    private static Object MONITOR = new Object();
    public static void main(String[] args) throws InterruptedException {

        Thread t1 = new Thread(()->{
            log.info("this is t1 thread");
            while(true){
                synchronized (MONITOR) {
                    try {
                        MONITOR.wait();
                    } catch (InterruptedException e) {
                        log.info("t1 is interrupted ? {}", Thread.currentThread().isInterrupted());
                    }
                }
            }
        });

        t1.start();
        TimeUnit.SECONDS.sleep(10);
        log.info("t1 is interrupted ? {}",t1.isInterrupted());
        t1.interrupt();
        TimeUnit.MILLISECONDS.sleep(3);
        log.info("t1 is interrupted ? {}",t1.isInterrupted());

    }
}
