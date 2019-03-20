package com.learn.designModel.SingletonPattern.Lazy;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * comment:懒汉式测试
 */
public class LazySingletonTest {

    public static void main(String[] args) throws InterruptedException {

        System.out.println("=======开始多线程的测试=======");

        Thread executeThread01 = new Thread(new ExecuteThread());
        executeThread01.start();

        Thread executeThread02 = new Thread(new ExecuteThread());
        executeThread02.start();
    }

}
