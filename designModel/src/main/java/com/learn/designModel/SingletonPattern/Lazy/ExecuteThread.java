package com.learn.designModel.SingletonPattern.Lazy;

/**
 * autor:liman
 * comment:
 */
public class ExecuteThread implements Runnable {
    @Override
    public void run() {
        LazySimpleSingleton lazySimpleSingleton = LazySimpleSingleton.getInstance();
        System.out.println(Thread.currentThread().getName()+":"+lazySimpleSingleton);
    }
}