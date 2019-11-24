package com.learn.executorpoollearn;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * autor:liman
 * createtime:2019/11/13
 * comment:简单的线程池的实例
 */
@Slf4j
public class ThreadPoolDemo {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(5);
        for(int i=0;i<10;i++){
            int index = i;
//            executorService.submit(new simpleThread(index));
            executorService.execute(new simpleThread(index));
        }

        executorService.shutdown();
    }

    static class simpleThread implements Runnable{

        private int index;

        public simpleThread(int index) {
            this.index = index;
        }

        @Override
        public void run() {
            log.info("i={}:executorService",index);
        }
    }

}
