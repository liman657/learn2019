package com.learn.lambda.MethodReference;

import java.util.function.Consumer;

/**
 * autor:liman
 * createtime:2019/8/6
 * comment:
 */
public class MethodReference {

    public static void main(String[] args) {
        Consumer<String> consumer = (s)-> System.out.println(s);
        useConsumer(consumer,"Hello liman");
        useConsumer((s)-> System.out.println(s),"liman");
        useConsumer(System.out::println,"hello liman");
    }

    /**
     * consumer有一个入参为泛型，返回值为void的函数
     * @param consumer
     * @param t
     * @param <T>
     */
    private static <T> void useConsumer(Consumer<T> consumer,T t){
        consumer.accept(t);
        consumer.accept(t);
    }

}
