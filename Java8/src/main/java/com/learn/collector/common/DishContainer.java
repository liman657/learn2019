package com.learn.collector.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * autor:liman
 * createtime:2019/8/14
 * comment:
 */
public class DishContainer {

    private static List<Dish> dishList = new ArrayList<>();

    public static List<Dish> getDishList(){
        dishList = Arrays.asList(
                new Dish("pork", false, 800, Dish.Type.MEAT),
                new Dish("beef", false, 700, Dish.Type.MEAT),
                new Dish("chicken", false, 400, Dish.Type.MEAT),
                new Dish("french fries", true, 530, Dish.Type.OTHER),
                new Dish("rice", true, 350, Dish.Type.OTHER),
                new Dish("season fruit", true, 120, Dish.Type.OTHER),
                new Dish("pizza", true, 550, Dish.Type.OTHER),
                new Dish("prawns", false, 300, Dish.Type.FISH),
                new Dish("salmon", false, 450, Dish.Type.FISH) );
        return dishList;
    }

}
