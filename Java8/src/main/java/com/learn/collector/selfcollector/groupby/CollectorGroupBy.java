package com.learn.collector.selfcollector.groupby;

import com.learn.collector.common.Dish;
import com.learn.collector.common.DishContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2020/5/23
 * comment:Collector的分组实例
 */
@Slf4j
public class CollectorGroupBy {

    public static void main(String[] args) {
        List<Dish> dishList = DishContainer.getDishList();
        testGoupBy(dishList);
        testMultiLevelGroupBy(dishList);
        testGroupByCount(dishList);
        testGroupByMax(dishList);
        testSimpleGroupBy(dishList);
    }

    /**
     * groupby的入门
     * @param dishList
     * groupingBy(Function<? super T, ? extends K> classifier)
     */
    public static void testGoupBy(List<Dish> dishList){
        Map<Dish.Type, List<Dish>> typeMapResult = dishList.stream().collect(Collectors.groupingBy(Dish::getType));
        log.info("step in groupby result:{},map size:{}",typeMapResult,typeMapResult.size());

        Map<CaloricLevel, List<Dish>> groupByCalories = dishList.stream().collect(
                Collectors.groupingBy(
                        dish -> {
                            if (dish.getCalories() <= 400) return CaloricLevel.DIET;
                            else if (dish.getCalories() <= 700) return CaloricLevel.NORMAL;
                            else return CaloricLevel.FAT;
                        }
                )
        );

        log.info("根据卡路里进行分组的结果:{}",groupByCalories);
    }

    /**
     * 测试多级分组
     * @param dishList
     */
    public static void testMultiLevelGroupBy(List<Dish> dishList){

        Function<Dish,CaloricLevel> deepGroupFunction = dish -> {
            if(dish.getCalories()<400) return CaloricLevel.DIET;
            else if(dish.getCalories()<=700) return CaloricLevel.NORMAL;
            else return CaloricLevel.FAT;
        };

        Map<Dish.Type, Map<CaloricLevel, List<Dish>>> deepGroupResult = dishList.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.groupingBy(deepGroupFunction)));

        log.info("多级分组的结果:{}",deepGroupResult);
    }

    public enum CaloricLevel{DIET,NORMAL,FAT};

    /**
     * 分组之后继续进行counting
     *  counting() {
     *      return reducing(0L, e -> 1L, Long::sum);
     *  }
     * @param dishList
     */
    public static void testGroupByCount(List<Dish> dishList){
        Map<Dish.Type, Long> groupByCountResult = dishList.stream().collect(Collectors.groupingBy(
                Dish::getType, Collectors.counting()
        ));

//        dishList.stream().collect(Dish::getType,Collectors.reducing(0,i->{1},Integer::sum));

        log.info("group by count:{}",groupByCountResult);
    }

    /**
     * 分组统计
     * @param dishList
     */
    public static void testGroupByMax(List<Dish> dishList){
        Map<Dish.Type, Optional<Dish>> maxWithGroupBy = dishList.stream().collect(Collectors.groupingBy(Dish::getType, Collectors.maxBy(Comparator.comparingInt(Dish::getCalories))));
        log.info("max with group by result : {}",maxWithGroupBy);
    }

    public static void testSimpleGroupBy(List<Dish> dishList){
        Map<Dish.Type, List<Dish>> result = dishList.stream().collect(Collectors.groupingBy(Dish::getType));
        log.info("simple group by result : {}",result);
    }

}
