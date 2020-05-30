package com.learn.collector.CollectorOne;

import com.learn.collector.common.Dish;
import com.learn.collector.common.DishContainer;
import sun.plugin.javascript.navig.LinkArray;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.DoubleSummaryStatistics;
import java.util.LinkedList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2019/10/5
 * comment:
 * collectors类的实例
 */
public class CollectorsActionForth {

    private static List<Dish> dishes = DishContainer.getDishList();

    public static void main(String[] args) {
        testSummingDouble();
        testSummingInt();
        testToCollections();
        testToConcurrentMap();
        testToConcurrentHashMapWithBinaryOperator();
    }

    /**
     * summingDouble
     */
    private static void testSummingDouble() {
        System.out.println("testSummingDouble");
        Optional.of(dishes.stream().collect(Collectors.summarizingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);

        Optional.of(dishes.stream().map(Dish::getCalories)
                .mapToInt(Integer::intValue).sum())
                .ifPresent(System.out::println);
    }

    private static void testSummingInt() {
        System.out.println("testSummingInt");
        Optional.of(dishes.stream().collect(Collectors.summarizingInt(Dish::getCalories)))
                .ifPresent(System.out::println);
    }

    /**
     * toCollections，将stream的数据放到自己指定的集合类型中
     */
    private static void testToCollections() {
        System.out.println("testToCollections");
        Optional.ofNullable(dishes.stream().filter(d -> d.getCalories() > 500).collect(Collectors.toCollection(LinkedList::new)))
                .ifPresent(System.out::println);
    }

    /**
     * 将stream的数据转换成从currentHashMap
     */
    private static void testToConcurrentMap() {
        System.out.println("testToConcurrentMap");
        Optional.ofNullable(dishes.stream().collect(Collectors.toConcurrentMap(Dish::getName, Dish::getCalories)))
                .ifPresent(v -> {
                    System.out.println(v);
                    System.out.println(v.getClass());
                });
    }

    private static void testToConcurrentHashMapWithBinaryOperator() {
        System.out.println("testToConcurrentHashMapWithBinaryOperator");
        Optional.ofNullable(dishes.stream().collect(
                Collectors.toConcurrentMap(Dish::getType, v -> 1L, (a, b) -> a + b, ConcurrentSkipListMap::new)
        )).ifPresent(v -> {
            System.out.println(v);
            System.out.println(v.getClass());
        });
    }

}
