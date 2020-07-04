package com.learn.thread.update.phase01.dameonthread;

import lombok.extern.slf4j.Slf4j;

/**
 * autor:liman
 * createtime:2020/6/6
 * comment:守护线程的实例
 */
@Slf4j
public class DaemonThreadDemo {

    public static void main(String[] args) throws InterruptedException {
        Thread thread = new Thread(()->{
//           while(true){
               try {
                   log.info("self thread start");
                   Thread.sleep(1);
                   log.info("self thread end");
               } catch (InterruptedException e) {
                   e.printStackTrace();
               }
//           }
        });
//        thread.setDaemon(true);
        thread.start();
        log.info("is main daemon : {}",Thread.currentThread().isDaemon());
        log.info("Main thread finished");
    }
}
