package com.learn.start;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

/**
 * autor:liman
 * createtime:2019/6/25
 * comment:
 */
public class StartByCallable implements Callable<Integer> {
    @Override
    public Integer call() throws Exception {
        int i = 100;
        Thread.sleep(5000);
        System.out.println(Thread.currentThread().getName() + ":" + i);
        return i;
    }

    public static void main(String[] args) {
        StartByCallable callableThread = new StartByCallable();
        FutureTask<Integer> ft = new FutureTask<>(callableThread);
        new Thread(ft).start();

        try {
            System.out.println("线程返回值:" + ft.get());
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println("还在主线程中");
    }
}
