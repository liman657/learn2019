package com.learn.threadPool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * autor:liman
 * createtime:2019/7/21
 * comment: ThreadPoolDemo——固定大小的线程池实例
 */
public class ThreadPoolDemo {

    public static void main(String[] args) {
        MyTask task = new MyTask();
        ExecutorService service = Executors.newFixedThreadPool(3);
        for(int i=0;i<10;i++){
            service.submit(task);
        }
    }

    public static class MyTask implements Runnable{

        @Override
        public void run() {
            System.out.println(System.currentTimeMillis()+":Thread name : "+Thread.currentThread().getName());

            try{
                Thread.sleep(1000);
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

}
