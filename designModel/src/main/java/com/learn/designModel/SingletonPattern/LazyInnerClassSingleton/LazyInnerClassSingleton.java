package com.learn.designModel.SingletonPattern.LazyInnerClassSingleton;

/**
 * autor:liman
 * comment:懒汉式单例，静态内部类的实现方式
 *
 * 没有synchronized关键字，性能更加高效
 *
 * 利用了类的加载顺序，内部的静态类会优先于外部类进行加载，加载内部类的时候，静态的逻辑就会执行
 *
 * JVM底层的执行逻辑，完美的避免了线程安全问题，这个需要详细讨论
 *
 * TODO：利用debug模式执行以下，就可以理解内部类的执行逻辑和生命周期
 */
public class LazyInnerClassSingleton {

    //问题：构造方法私有了，但是反射可以破坏这个访问权限
    private LazyInnerClassSingleton(){}

    public static final LazyInnerClassSingleton getInstance(){

        //外部内不调用这个方法的时候，内部类的逻辑是不会加载的
        return LazyHolder.lazyInnerClassSingleton;
    }

    private static class LazyHolder{
        private static final LazyInnerClassSingleton lazyInnerClassSingleton= new LazyInnerClassSingleton();
    }

}
