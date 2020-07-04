package com.learn.thread.update.phase01.threadstop;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2020/6/7
 * comment:优雅的停止一个线程
 */
@Slf4j
public class StopThreadGraceful {

    public static void main(String[] args) throws InterruptedException {
        Thread t1 = new Thread(()->{
            while(true){
                log.info("t1 will start work");
                while(!Thread.currentThread().isInterrupted()){
                    // doing work;
                }
                log.info("t1 will exit");
            }
        });

        t1.start();
        TimeUnit.SECONDS.sleep(1);
        log.info("main will stop t1");
        t1.interrupt();
    }

    public static class MyTask extends Thread{
        private volatile boolean isClosed = false;

        @Override
        public void run() {
            while(true){
                log.info("t1 will start work");
                while(!isClosed&&isInterrupted()){
                    // doing work;
                }
                log.info("t1 will exit");
                break;
            }
        }

        public void close(){
            this.isInterrupted();
            this.isClosed=true;
        }
    }
}
