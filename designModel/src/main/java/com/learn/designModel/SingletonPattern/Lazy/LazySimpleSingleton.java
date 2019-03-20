package com.learn.designModel.SingletonPattern.Lazy;

/**
 * autor:liman
 * comment:懒汉式单例
 */
public class LazySimpleSingleton {

    private static LazySimpleSingleton lazySimpleSingle = null;
    private LazySimpleSingleton(){}

    public static LazySimpleSingleton getInstance(){

        //这里会出现线程不安全的情况，如果同时进入会出现new多次的情况
        if(lazySimpleSingle==null) {
            synchronized (LazySimpleSingleton.class){
                if(lazySimpleSingle == null){
                    lazySimpleSingle = new LazySimpleSingleton();
                }
            }
        }
        return lazySimpleSingle;
    }
}