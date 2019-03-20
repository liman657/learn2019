package com.learn.designModel.SingletonPattern.LazyInnerClassSingleton;

/**
 * autor:liman
 * comment:
 */
public class LazyInnerClassTest {
    public static void main(String[] args) {
        LazyInnerClassSingleton lazyInnerClassSingleton01 = LazyInnerClassSingleton.getInstance();
        LazyInnerClassSingleton lazyInnerClassSingleton02 = LazyInnerClassSingleton.getInstance();

        System.out.println(lazyInnerClassSingleton01 == lazyInnerClassSingleton02);

    }
}
