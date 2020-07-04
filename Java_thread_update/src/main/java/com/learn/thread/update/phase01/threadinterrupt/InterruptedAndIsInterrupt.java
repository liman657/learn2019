package com.learn.thread.update.phase01.threadinterrupt;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/7
 * comment:判断线程的中断标志位
 */
@Slf4j
public class InterruptedAndIsInterrupt {

    public static void main(String[] args)  {
//        Thread t1 = new Thread(()->{
//            while(true){
//                if(Thread.interrupted()){
//                    log.error("ERROR");
//                }else{
//                    log.info("t1 is interrupted:{}",Thread.interrupted());
//                    break;
//                }
//            }
//        });
//        t1.start();
//        TimeUnit.SECONDS.sleep(3);
//        t1.interrupt();

        log.info("main thread is interrupted :{}",Thread.interrupted());
        Thread.currentThread().interrupt();
        log.info("main thread is interrupted : {}",Thread.currentThread().isInterrupted());
        try {
            log.info("main is running");
            TimeUnit.SECONDS.sleep(10);
        } catch (InterruptedException e) {
            log.error("test error");
        }
    }



}
