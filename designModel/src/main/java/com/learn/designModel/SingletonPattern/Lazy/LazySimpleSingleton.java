package com.learn.designModel.SingletonPattern.Lazy;

/**
 * autor:liman
 * comment:懒汉式单例
 */
public class LazySimpleSingleton {

    //为了避免jvm层面的new对象指令重排，这里加入volatile关键字
    private static volatile LazySimpleSingleton lazySimpleSingle = null;
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