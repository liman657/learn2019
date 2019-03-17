package com.learn.designModel.SingletonPattern.LazyDoubleCheck;

/**
 * autor:liman
 * comment:懒汉式单例，双重锁检测写法
 */
public class LazyDubleCheckSingleton {

    private static LazyDubleCheckSingleton lazyDubleCheckSingleton = null;

    private LazyDubleCheckSingleton(){}

    public static LazyDubleCheckSingleton getInstance(){

        if(lazyDubleCheckSingleton==null) {
            synchronized (LazyDubleCheckSingleton.class){
                if(lazyDubleCheckSingleton == null) {
                    lazyDubleCheckSingleton = new LazyDubleCheckSingleton();
                }
            }

        }

        //如果一个线程在返回之前，另一个线程进入了同步代码块，会new两次
        return lazyDubleCheckSingleton;
    }

}
