package com.learn.collector.CollectorOne;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2019/9/22
 * comment:Collectors api的使用
 */
public class CollectorsActionOne {

    private static List<Dish> dishes = DishContainer.getDishList();

    public static void main(String[] args) {
        testAveragingDouble();
        testAveragingInt();
        testAveragingLong();
        testCollectorAndThen();
        testCounting();
        testGroupingByFunction();
        testGroupingByOtherOpera();
        testGroupingByChangeReturnType();
        testSummarizingInt();
    }

    public static void testAveragingDouble() {
        System.out.print("test averaging double result : ");
        Optional.ofNullable(dishes.stream().collect(Collectors.averagingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    public static void testAveragingInt() {
        System.out.print("test averaging int result : ");
        Optional.ofNullable(dishes.stream().collect(Collectors.averagingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    public static void testAveragingLong() {
        System.out.print("test averaging long result : ");
        Optional.ofNullable(dishes.stream().collect(Collectors.averagingLong(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    /**
     * collectingAndThen，第一个参数就是正常的集合归约操作，第二个参数指定收集之后的操作。
     */
    public static void testCollectorAndThen() {
        System.out.print("test collector and then result : ");
        Optional.ofNullable(dishes.stream().collect(
                Collectors.collectingAndThen(Collectors.averagingDouble(Dish::getCalories),
                        a -> "this final average result is " + a))).ifPresent(System.out::println);


//        Dish test = new Dish("test", true, 100, Dish.Type.OTHER);
//        //这里得到的meatList是可以修改的。
//        List<Dish> meatList = dishes.stream().filter(d -> d.getType().equals(Dish.Type.MEAT)).collect(Collectors.toList());
//        meatList.add(test);
//
//        List<Dish> unModifyList = dishes.stream().filter(d -> d.getType().equals(Dish.Type.MEAT))
//                .collect(Collectors.collectingAndThen(Collectors.toList()
//                , Collections::unmodifiableList));//返回的集合不希望被修改
//        unModifyList.add(test);//这里会抛出异常，UNsupportedOperationException
//        System.out.println(unModifyList);
    }

    public static void testCounting() {
        System.out.print("test counting result : ");
        Optional.ofNullable(dishes.stream().collect(Collectors.counting()))
                .ifPresent(System.out::println);
    }

    public static void testGroupingByFunction() {
        System.out.print("test grouping by function result : ");
//        Map<Dish.Type, List<Dish>> groupByTypeResult = ;
        Optional.ofNullable(dishes.stream().collect(Collectors.groupingBy(Dish::getType)))
                .ifPresent(System.out::println);
    }

    /**
     * 在分类的基础上对每一中类型进行进一步操作
     * Collectors.groupingBy中的第一个参数是用于分类的参数，第二个参数是针对每一种分类的统计操作，可以是计数，也可以是求平均值
     */
    public static void testGroupingByOtherOpera() {
        System.out.print("test grouping by function with counting result : ");
//        Map<Dish.Type, List<Dish>> groupByTypeResult = ;
        Optional.ofNullable(dishes.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.counting())))
                .ifPresent(System.out::println);

        Optional.ofNullable(dishes.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.averagingDouble(Dish::getCalories))))
                .ifPresent(System.out::println);
    }

    /**
     * groupingBy的第二个参数就是转换的目标类型
     */
    public static void testGroupingByChangeReturnType() {
        System.out.print("test grouping by with change return type result:");
        Map<Dish.Type, Double> firstTypeResult = dishes.stream()
                .collect(Collectors.groupingBy(Dish::getType, TreeMap::new, Collectors.averagingDouble(Dish::getCalories)));

        Optional.ofNullable(firstTypeResult).ifPresent(System.out::println);
        Optional.ofNullable(firstTypeResult.getClass()).ifPresent(System.out::println);
    }

    /**
     * summarize中文即为统计的意思，Collectors.summarizingInt只需要传入一个对象的属性，即可统计出这个属性的平均值，最大值和最小值等
     */
    public static void testSummarizingInt() {
        System.out.print("test summarizing int result:");
        Optional.ofNullable(dishes.stream().collect(Collectors.summarizingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

}
