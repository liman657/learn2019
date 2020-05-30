package com.learn.collector.CollectorOne;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collector;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2019/9/22
 * comment:
 */
@Slf4j
public class CollectorsActionTwo {

    private static List<Dish> dishes = DishContainer.getDishList();

    public static void main(String[] args) {
        testGroupingByConcurrent();
        testGroupingByConcurrentFunction();
        testGroupingByConcurrentChangeType();
        testJoin();
        testJoinWithDelimiter();
        testJoinWithDelimiterAndSufAndPrefix();
        testMapping();
        testMaxBy();
        testMinBy();
    }

    public static void testGroupingByConcurrent() {
        System.out.print("test grouping by concurrent result : ");
        Optional.ofNullable(dishes.stream().collect(Collectors.groupingByConcurrent(Dish::getType)))
                .ifPresent(System.out::println);
    }

    public static void testGroupingByConcurrentFunction() {
        System.out.print("test grouping by concurrent function result : ");
        Optional.ofNullable(dishes.stream()
                .collect(Collectors.groupingByConcurrent(Dish::getType, Collectors.averagingDouble(Dish::getCalories))))
                .ifPresent(System.out::println);
    }

    public static void testGroupingByConcurrentChangeType() {
        System.out.print("test grouping by concurrent changeType result : ");
        Optional<ConcurrentMap<Dish.Type, Double>> orginalType = Optional.ofNullable(dishes.stream()
                .collect(Collectors.groupingByConcurrent(Dish::getType,
                        Collectors.averagingDouble(Dish::getCalories))));
        System.out.println(orginalType.get().getClass());

        Optional<ConcurrentSkipListMap<Dish.Type, Double>> changeTypeResult = Optional.ofNullable(dishes.stream()
                .collect(Collectors.groupingByConcurrent(Dish::getType, ConcurrentSkipListMap::new,
                        Collectors.averagingDouble(Dish::getCalories))));

        System.out.println(changeTypeResult.get().getClass());
    }

    /**
     * join，其实就是将获取的以字符串的形式做一个汇总，参数只能为String类型的。
     */
    public static void testJoin() {
        System.out.print("test join result : ");
        Optional.ofNullable(dishes.stream().map(Dish::getName)
                .collect(Collectors.joining())).ifPresent(System.out::println);
    }

    public static void testJoinWithDelimiter() {
        System.out.print("test join limiter result : ");
        Optional.ofNullable(dishes.stream().map(Dish::getName)
                .collect(Collectors.joining(","))).ifPresent(System.out::println);
    }

    public static void testJoinWithDelimiterAndSufAndPrefix() {
        System.out.print("test join limiter and sufPrefix result : ");
        Optional.ofNullable(dishes.stream().map(Dish::getName)
                .collect(Collectors.joining(",", "names[", "]"))).ifPresent(System.out::println);
    }

    /**
     * Mapping的操作类似于stream的map操作，但是这个操作需要join辅助返回结果
     */
    public static void testMapping() {
        System.out.print("test mapping result:");
        Optional.ofNullable(dishes.stream().collect(Collectors.mapping(Dish::getName, Collectors.joining(","))))
                .ifPresent(System.out::println);
    }

    /**
     * 根据指定的比较器返回最大的集合元素
     */
    public static void testMaxBy(){
        System.out.print("test max by result : ");
        Optional.ofNullable(dishes.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))))
                .ifPresent(System.out::println);
    }

    /**
     * 根据指定的比较器返回最小的集合元素
     */
    public static void testMinBy(){
        System.out.print("test min by result : ");
        Optional.ofNullable(dishes.stream().collect(Collectors.minBy(Comparator.comparingInt(Dish::getCalories))))
                .ifPresent(System.out::println);
    }
}
