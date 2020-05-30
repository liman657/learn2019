package com.learn.collector.selfcollector.reduceAndSummary;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.collectingAndThen;
import static java.util.stream.Collectors.summingInt;

/**
 * autor:liman
 * createtime:2020/5/23
 * comment:利用collector进行归约和汇总
 */
@Slf4j
public class ReduceAndSummary {

    public static void main(String[] args) {
        List<Dish> dishList = DishContainer.getDishList();
        testCollectorCount(dishList);
        testGetMaxAndMin(dishList);
        testSummingInt(dishList);
        testJoin(dishList);
        reducingTotalCalories(dishList);
    }

    /**
     * 测试count的方法
     */
    public static void testCollectorCount(List<Dish> dishList) {
        Long dishCount = dishList.stream().collect(Collectors.counting());
        log.info("discount num : {}", dishCount);
    }

    /**
     * 获取流中的最大值和最小值
     *
     * @param dishList
     */
    public static void testGetMaxAndMin(List<Dish> dishList) {
        dishList.stream().collect(Collectors.maxBy(Comparator.comparingInt(Dish::getCalories)))
                .ifPresent(e -> log.info("max calory dish:{}", e));
//        Comparator<Dish> dishCaloryComparator = Comparator.comparingInt(Dish::getCalories);

        dishList.stream().collect(Collectors.minBy(Comparator.comparingInt(Dish::getCalories)))
                .ifPresent(e -> log.info("min calory dish:{}", e));
    }

    /**
     * 统计所有的calorie
     *
     * @param dishList
     */
    public static void testSummingInt(List<Dish> dishList) {
        Integer totalCalories = dishList.stream().collect(Collectors.summingInt(Dish::getCalories));
        log.info("all calories:{}", totalCalories);

        Double averageCalories = dishList.stream().collect(Collectors.averagingInt(Dish::getCalories));
        log.info("average calories:{}", averageCalories);

        IntSummaryStatistics summaryStatistics = dishList.stream().collect(Collectors.summarizingInt(Dish::getCalories));
        log.info("summary info : {}",summaryStatistics);
    }

    /**
     * @param dishList
     */
    public static void testJoin(List<Dish> dishList) {
        String allDishName = dishList.stream().map(Dish::getName).collect(Collectors.joining());
        log.info("all dish name with no spilt : {}", allDishName);

        String allDishNameWithSpilt = dishList.stream().map(Dish::getName).collect(Collectors.joining(", "));
        log.info("all dish name with spilt:{}", allDishNameWithSpilt);
    }

    /**
     * @param dishList
     */
    public static void reducingTotalCalories(List<Dish> dishList) {
        Integer totalCalories = dishList.stream().collect(Collectors.reducing(0, Dish::getCalories, (i, j) -> i + j));
        log.info("reducing total calories:{}", totalCalories);

        Dish dish = dishList.stream().collect(Collectors.reducing((d1, d2) -> d1.getCalories() > d2.getCalories() ? d1 : d2)).orElse(new Dish("null", false, 0, Dish.Type.OTHER));
        log.info("calories max dish:{}",dish);

    }


}

