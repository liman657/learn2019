package com.learn.collector.CollectorOne;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.function.BinaryOperator;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2019/9/22
 * comment:
 */
public class CollectorsActionThree {

    private static List<Dish> dishes = DishContainer.getDishList();

    public static void main(String[] args) {
        testPartitionByWithPredicate();
        testParatitionByWithPredicateAndCollector();
        testReducingBinaryOperator();
        testReducingBinaryOperatorAndIdentity();
        testReducingBinaryOperatorIdentityAndFunction();
    }

    /**
     * 指定predicate的结果进行分组
     */
    public static void testPartitionByWithPredicate(){
        System.out.println("testPartitionByWithPredicate");
        Map<Boolean, List<Dish>> result = dishes.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        Optional.of(result).ifPresent(System.out::println);
    }

    /**
     * 对predicate的结果，进一步进行collectors
     */
    public static void testParatitionByWithPredicateAndCollector(){
        System.out.println("testParatitionByWithPredicateAndCollector");
        Map<Boolean, Double> result = dishes.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.averagingDouble(Dish::getCalories)));
        Optional.of(result).ifPresent(System.out::println);
    }

    /**
     * 注意这个实例中的Comparator的操作
     */
    public static void testReducingBinaryOperator(){
        System.out.println("testReducingBinaryOperator");
        Optional<Dish> result = dishes.stream().collect(Collectors.reducing(BinaryOperator.maxBy(Comparator.comparing(Dish::getCalories))));
        System.out.println(result);
    }

    public static void testReducingBinaryOperatorAndIdentity(){
        System.out.println("testReducingBinaryOperatorAndIdentity");
        Integer result = dishes.stream().map(Dish::getCalories).collect(Collectors.reducing(0, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }

    public static void testReducingBinaryOperatorIdentityAndFunction(){
        System.out.println("testReducingBinaryOperatorIdentityAndFunction");
        Integer result = dishes.stream().collect(Collectors.reducing(0, Dish::getCalories, (d1, d2) -> d1 + d2));
        System.out.println(result);
    }
}