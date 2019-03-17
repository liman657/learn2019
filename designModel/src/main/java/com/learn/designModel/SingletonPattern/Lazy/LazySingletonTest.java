package com.learn.designModel.SingletonPattern.Lazy;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * comment:懒汉式测试
 */
public class LazySingletonTest {

    public static void main(String[] args) throws InterruptedException {
//        LazySimpleSingleton lazySimpleSingleton = LazySimpleSingleton.getInstance();
//        System.out.println(lazySimpleSingleton);
//
//        LazySimpleSingleton lazySimpleSingleton2 = LazySimpleSingleton.getInstance();
//        System.out.println(lazySimpleSingleton2);

        System.out.println("=======开始多线程的测试=======");

//        for (int i = 0; i < 100; i++) {
            Thread executeThread01 = new Thread(new ExecuteThread());
            executeThread01.start();
//            Thread.sleep(10);

        Thread executeThread02 = new Thread(new ExecuteThread());
        executeThread02.start();
//        }
    }

}
