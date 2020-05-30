package com.learn.collector.selfcollector.groupby;

import com.learn.collector.common.Dish;
import com.learn.collector.common.DishContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 * autor:liman
 * createtime:2020/5/25
 * comment:分区——特殊的分组
 */
@Slf4j
public class CollectorPartition {

    public static void main(String[] args) {
        List<Dish> dishList = DishContainer.getDishList();
        Map<Boolean, List<Dish>> result = dishList.stream().collect(Collectors.partitioningBy(Dish::isVegetarian));
        log.info("{}", result);

        List<Dish> filterResult = dishList.stream().filter(Dish::isVegetarian).collect(Collectors.toList());
        log.info("{}", filterResult);

        testDeepPartition();

        departPrimeAndNoPrime(20);
    }

    public static void testDeepPartition() {
        List<Dish> dishList = DishContainer.getDishList();
        Map<Boolean, Map<Dish.Type, List<Dish>>> result = dishList.stream().collect(Collectors.partitioningBy(Dish::isVegetarian, Collectors.groupingBy(Dish::getType)));
        log.info("{}", result);
    }

    /**
     * 判断是不是质数的函数
     *
     * @param candidate
     * @return
     */
    public static boolean isPrime(int candidate) {
        int candidateRoot = (int) Math.sqrt((double) candidate);
        return IntStream.rangeClosed(2, candidateRoot)
                .noneMatch(i -> candidate % i == 0);//如果一个都没法整除，则是质数
    }

    /**
     * 将数据流分成质数和非质数
     */
    public static void departPrimeAndNoPrime(int n) {
        Map<Boolean, List<Integer>> result = IntStream.range(2, n).boxed()
                .collect(Collectors.partitioningBy(candidate -> isPrime(candidate)));
        log.info("all result:{}",result);
        log.info("prime result : {}",result.get(true));
    }


}
