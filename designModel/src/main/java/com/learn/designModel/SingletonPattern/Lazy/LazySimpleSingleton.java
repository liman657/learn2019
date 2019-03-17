package com.learn.designModel.SingletonPattern.Lazy;

/**
 * autor:liman
 * comment:懒汉式单例
 */
public class LazySimpleSingleton {

    private static LazySimpleSingleton lazySimpleSingle = null;

    private LazySimpleSingleton(){}

    //所以这里会加入synchronized
    public static synchronized LazySimpleSingleton getInstance(){

        //这里会出现线程不安全的情况，如果同时进入会出现new多次的情况

        if(lazySimpleSingle==null) {

            lazySimpleSingle = new LazySimpleSingleton();
        }
        return lazySimpleSingle;

//        if(lazySimpleSingle!=null){
//            return lazySimpleSingle;
//        }
//        lazySimpleSingle = new LazySimpleSingleton();
//        return lazySimpleSingle;
    }

}
