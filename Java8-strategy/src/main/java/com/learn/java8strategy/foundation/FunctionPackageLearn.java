package com.learn.java8strategy.foundation;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.DoubleSupplier;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2020/12/20
 * comment:function包，这个是java8中提供的函数式接口的包
 * 对应java攻略:JAVA常见问题的简单解法一书中的第二章
 */
@Slf4j
public class FunctionPackageLearn {

    public static void main(String[] args) {
        consumerInterfaceDemo();
        predicateInterfaceDemo();
    }

    /**
     * Comsumer接口的实例
     * void accept(T t); 未实现的方法签名
     * default Consumer<T> andThen(Consumer<? super T> after) 默认的实现方法
     */
    public static void consumerInterfaceDemo(){
        List<String> strings = Arrays.asList("this","is","a","list","of","strings");

        //Iterable接口中 default void forEach(Consumer<? super T> action)
        //匿名内部类的实现
        strings.forEach(new Consumer<String>() {
            @Override
            public void accept(String s) {
                System.out.println(s);
            }
        });

        //lambda表达式的实现
        strings.forEach(s-> System.out.println(s));
        //方法引用的实现
        strings.forEach(System.out::println);
    }

    /**
     * Supplier接口实例
     * T get(); 只有这一个抽象方法
     * Supplier主要用处是延迟执行 Logger类中定义的info会传入这个
     * Optioal的orElseGet方法参数也是这个
     * public T orElseGet(Supplier<? extends T> other)
     */
    public static void supplierInterfaceDemo(){

        //匿名内部类的实现
        DoubleSupplier randomSupplier = new DoubleSupplier() {
            @Override
            public double getAsDouble() {
                return Math.random();
            }
        };

        //lambda表达式
        randomSupplier = ()->Math.random();
        //方法引用
        randomSupplier = Math::random;

    }

    /**
     * Predicate接口实例
     * predicate可进行多个Predicate的组合
     * boolean test(T t); 为Predicate接口中单一抽象方法
     * Predicate接口主要用于流的筛选，给定一个包含若干项的流，Stream的filter方法接受一个Predicate的参数
     * Stream中的 Stream<T> filter(Predicate<? super T> predicate);
     * Stream中的 boolean allMatch(Predicate<? super T> predicate);
     * Collection中的 default boolean removeIf(Predicate<? super E> filter)
     * Collectors中的
     * public static <T>
     *     Collector<T, ?, Map<Boolean, List<T>>> partitioningBy(Predicate<? super T> predicate)
     */
    public static void predicateInterfaceDemo(){
        List<String> bonds = Arrays.asList("Connery", "Lazenby", "Moore", "Dalton", "Brosnan", "Craig");
        List<String> less7LengthList = bonds.stream().filter(s -> s.length() <= 7).collect(Collectors.toList());
        log.info("filter length less 7 info : {}",less7LengthList);

        List<String> startWithFilter = bonds.stream().filter(s -> s.startsWith("C")).collect(Collectors.toList());
        log.info("start with filter result:{}",startWithFilter);

        //定义两个predicate
        Predicate<String> LENGTH_LESS_SEVEN = s->s.length()<=5;
        Predicate<String> STARTS_WITH_C = s->s.startsWith("C");

        //并操作的复合
        List<String> andPredicateResult = bonds.stream().filter(LENGTH_LESS_SEVEN.and(STARTS_WITH_C)).collect(Collectors.toList());
        log.info("and predicate result:{}",andPredicateResult);

        //或操作的复合
        List<String> orPredicateResult = bonds.stream().filter(LENGTH_LESS_SEVEN.or(STARTS_WITH_C)).collect(Collectors.toList());
        log.info("or predicate result:{}",orPredicateResult);

        //取反操作
        List<String> negatePredicateResult = bonds.stream().filter(LENGTH_LESS_SEVEN.negate()).collect(Collectors.toList());
        log.info("negate predicate result:{}",negatePredicateResult);

    }

    /**
     * Function接口实例
     * R apply(T t); 唯一一个抽象方法
     * 这里只是一个简单的实例，后续第五章会有更复杂的实例
     */
    public static void functionInterfaceDemo(){
        List<String> bonds = Arrays.asList("Connery", "Lazenby", "Moore", "Dalton", "Brosnan", "Craig");
        //内部类
        List<Integer> innerClassFunction = bonds.stream().map(new Function<String, Integer>() {
            @Override
            public Integer apply(String s) {
                return s.length();
            }
        }).collect(Collectors.toList());
        log.info("inner class subMenuList:{}",innerClassFunction);

        //lambda实现
        List<Integer> lambdaLenthList = bonds.stream().map(s -> s.length()).collect(Collectors.toList());
        log.info("lambdaLengthList:{}",lambdaLenthList);

        //方法引用实现
        List<Integer> methodReferenceLength = bonds.stream().map(String::length).collect(Collectors.toList());
        log.info("methodReferenceLength:{}",methodReferenceLength);


    }




}
