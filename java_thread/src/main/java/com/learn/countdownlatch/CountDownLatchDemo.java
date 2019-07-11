package com.learn.countdownlatch;

import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * author:liman
 * createtime:2019/7/11
 */
public class CountDownLatchDemo implements Runnable{

    static final CountDownLatch end = new CountDownLatch(10);

    static final CountDownLatchDemo demo = new CountDownLatchDemo();

    @Override
    public void run() {
        try {
            Thread.sleep(new Random().nextInt(10)*1000);
            System.out.println("check complete");
            end.countDown();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) throws InterruptedException {
        ExecutorService exec = Executors.newFixedThreadPool(10);
        for(int i=0;i<10;i++){
            exec.submit(demo);
        }
        end.await();//要求主线程等待所有countDownLatch的线程完成，这里是在main线程中调用的该方法
        System.out.println("Fire");
        exec.shutdown();
    }
}
