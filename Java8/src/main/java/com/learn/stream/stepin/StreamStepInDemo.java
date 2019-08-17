package com.learn.stream.stepin;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

import static java.util.stream.Collectors.toList;

/**
 * autor:liman
 * createtime:2019/8/14
 * comment: 理解流的实例
 */
public class StreamStepInDemo {

    public static void main(String[] args) {
        List<Dish> dishList = DishContainer.getDishList();
        List<String> dishNameLowCaloriesByStream = getDishNameLowCaloriesByStream(dishList);
//        List<String> dishNameByCalories = getDishNameByCalories(dishList);
        System.out.println(dishNameLowCaloriesByStream);
    }

    /**
     * 获取热量小于400的菜的名字
     * @param dishes
     * @return
     */
    public static List<String> getDishNameByCalories(List<Dish> dishes){
        List<Dish> lowCaolories = new ArrayList<Dish>();
        for(Dish dish:dishes){
            if(dish.getCalories()<400){
                lowCaolories.add(dish);
            }
        }
        Collections.sort(lowCaolories,(d1,d2)->Integer.compare(d1.getCalories(),d2.getCalories()));

        List<String> names = new ArrayList<>();
        for(Dish dish:lowCaolories){
            names.add(dish.getName());
        }
        return names;
    }

    /**
     *
     * @param dishes
     * @return
     */
    public static List<String> getDishNameLowCaloriesByStream(List<Dish> dishes){
        return dishes.stream()
                .filter(d->d.getCalories()<400)
                .sorted(Comparator.comparing(Dish::getCalories))
                .map(Dish::getName)
                .collect(toList());
    }

}
