package com.learn.collector.CollectorOne;

import com.learn.collector.common.Dish;
import com.learn.collector.common.DishContainer;

import javax.swing.plaf.synth.SynthTextAreaUI;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.Optional;
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
    }

    private static void testSummingDouble() {
        System.out.println("testSummingDouble");
        Optional.of(dishes.stream().collect(Collectors.summarizingDouble(Dish::getCalories)))
                .ifPresent(System.out::println);

        Optional.of(dishes.stream().map(Dish::getCalories)
                .mapToInt(Integer::intValue).sum())
                .ifPresent(System.out::println);
    }

}
