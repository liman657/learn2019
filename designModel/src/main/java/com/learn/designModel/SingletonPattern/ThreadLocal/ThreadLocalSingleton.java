package com.learn.designModel.SingletonPattern.ThreadLocal;

/**
 * autor:liman
 * comment:基于ThreadLocal的单例模式
 */
public class ThreadLocalSingleton {

    private ThreadLocalSingleton(){}


    private static final ThreadLocal<ThreadLocalSingleton> threadLocalInstance = new ThreadLocal<ThreadLocalSingleton>(){
        @Override
        protected ThreadLocalSingleton initialValue() {
            return new ThreadLocalSingleton();
        }
    };

    public static ThreadLocalSingleton getInstance(){
        return threadLocalInstance.get();
    }
}