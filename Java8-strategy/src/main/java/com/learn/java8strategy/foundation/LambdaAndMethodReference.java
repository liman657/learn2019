package com.learn.java8strategy.foundation;

import com.learn.java8strategy.foundation.common.Person;
import lombok.extern.slf4j.Slf4j;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2020/12/17
 * comment: Java8的基础知识，lambda表达式，方法引用，构造函数引用，函数式接口等基础知识
 * 对应java攻略:JAVA常见问题的简单解法一书中的第一章
 */
@Slf4j
public class LambdaAndMethodReference {

    public static void main(String[] args) {
        lambdaDemo();
        methodReference();
        staticMethodInInterface();
    }

    /**
     * lambda表达式
     */
    public static void lambdaDemo() {
        //静态内部类的方式创建线程
        new Thread(new Runnable() {
            public void run() {
                System.out.println("静态内部类的方式构建线程对象");
            }
        }, "innerClassThread").start();

        //lambda构造的线程对象
        new Thread(() -> {
            System.out.println("lambda构造的线程对象");
        }, "lambdaThread").start();

        Runnable r = () -> {
            System.out.println("lambda表达式赋值给函数式接口的线程");
        };
        new Thread(r, "lambda2Runnable").start();
    }

    /**
     * 方法引用
     */
    public static void methodReference() {
        Stream.of(1, 2, 3, 4, 5, 6).forEach((x) -> System.out.print(x + " "));

        System.out.println();

        Stream.of(1, 2, 3, 4, 5, 5).forEach(System.out::print);

        System.out.println();

        Consumer<Integer> consumer = System.out::print;
        Stream.of(1, 2, 3).forEach(consumer);

        /**
         * 需要记住一点的是，lambda表达式和方法引用在任何情况下都不能脱离上下文的存在（这里看这句话可能有点懵）
         * 例如：
         * x->System.out.println(x)，这里的x参数就是上下文提供的。
         * (x,y)->Math.max(x,y)，这里上下文提供了两个参数。
         * 如果通过类名引用传入多个参数的方法，则上下文提供的第一个元素将作为方法的目标（也就是调用方法的对象），其他元素作为参数
         */
        //这里采用了两种常见的方法引用方式
        //1.Class::staticMethod 2.object::instanceMethod
        Stream.generate(Math::random).limit(10).forEach(s -> System.out.print(s + " "));

        //其实还有一种
        //3.Class::instanceMethod
        //这种方式让人迷惑，因为一般类名是调用静态方法的，而这里是调用实例方法
        List<String> strings = Arrays.asList("this", "is", "a", "list", "of", "strings");
        //sorted方法传入的参数为上下文提供的两个参数，类似于s1.compareTo(s2);
        List<String> sorted1 = strings.stream().sorted((s1, s2) -> s1.compareTo(s2)).collect(Collectors.toList());
        //sorted方法传入的类型为第三种方法引用——Class::instanceMethod
        List<String> sorted2 = strings.stream().sorted(String::compareTo).collect(Collectors.toList());

        //以下两种写法等效
        strings.stream().map(String::length).forEach(System.out::println);
        strings.stream().map(x -> x.length()).forEach(x -> System.out.print(x));
    }

    /**
     * java8构造函数的引用
     */
    public static void constructReference() {
        List<String> names = Arrays.asList("Tom", "Grace", "Ada", "Karen");
        //
        List<Person> peopleList = names.stream().map(name -> new Person(name)).collect(Collectors.toList());
        //使用构造函数引用来实例化Person
        List<Person> personList = names.stream().map(Person::new).collect(Collectors.toList());

        //stream接口中有一个<A> A[] toArray(IntFunction<A[]> generator);的签名
        Person[] personArray = names.stream().map(Person::new).toArray(Person[]::new);
    }

    /**
     * Java8中，我们可以随时为接口添加静态方法，步骤如下
     * 1、为方法添加static关键字
     * 2、提供一种无法被重写的实现
     * 3、通过接口名来访问这个静态方法
     * <p>
     * Java8针对Comparator接口就加入了如下几个静态的实现方法
     * comparing,comparingInt,comparingLong,comparingDouble
     * naturalOrder,reverseOrder 这两个接口从名字上可以看出就是用来排序的。
     */
    public static void staticMethodInInterface() {
        List<String> bonds = Arrays.asList("Connery", "Lazenby", "Moore", "Dalton", "Brosnan", "Craig");

        /**
         * 自然顺序排序（字典顺序）
         */
        //naturalOrder
        List<String> naturalOrderList = bonds.stream()
                .sorted(Comparator.naturalOrder())
                .collect(Collectors.toList());
        log.info("naturalOrderList:{}", naturalOrderList);

        /**
         * 反向顺序（字典的方向顺序）
         */
        //reverseOrder
        List<String> reverseOrderList = bonds.stream()
                .sorted(Comparator.reverseOrder())
                .collect(Collectors.toList());
        log.info("reverseOrderList:{}", reverseOrderList);

        /**
         * 按照小写名称排序
         */
        //comparing
        List<String> comparingList = bonds.stream()
                .sorted(Comparator.comparing(String::toLowerCase))
                .collect(Collectors.toList());
        log.info("comparingList:{}", comparingList);

        /**
         * 按照长度排序
         */
        //comparingInt
        List<String> comparingIntList = bonds.stream()
                .sorted(Comparator.comparingInt(String::length))
                .collect(Collectors.toList());
        log.info("comparingIntList:{}", comparingIntList);

        /**
         * 按照长度排序，如果长度相同则按照字典顺序排序
         */
        //comparingIntAndNatural
        List<String> comparingIntAndNaturalList = bonds.stream()
                .sorted(Comparator.comparingInt(String::length).thenComparing(Comparator.naturalOrder()))
                .collect(Collectors.toList());
        log.info("comparingIntAndNaturalList:{}", comparingIntAndNaturalList);

    }

}
