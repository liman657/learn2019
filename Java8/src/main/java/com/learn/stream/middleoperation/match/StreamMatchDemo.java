package com.learn.stream.middleoperation.match;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2019/8/26
 * comment:
 */
public class StreamMatchDemo {

    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
//        boolean match = stream.allMatch(i -> i > 10);
//        System.out.println(match);

//        boolean anyMatchBoolean = stream.anyMatch(i -> i > 2);
//        System.out.println(anyMatchBoolean);

        Optional<Integer> firstNum = stream.map(x -> x * x).filter(x -> x % 3 == 0).findFirst();
        System.out.println(firstNum.get());

//        boolean noneMatchBoolean = stream.noneMatch(i -> i > 10);
//        System.out.println(noneMatchBoolean);

        List<Dish> dishes = DishContainer.getDishList();
        boolean hasVegetarian = dishes.stream().anyMatch(Dish::isVegetarian);
        if(hasVegetarian){
            System.out.println("the menu is vegetarian friendly;");
        }

        boolean isHealthy = dishes.stream().allMatch(d -> d.getCalories() < 1000);
        if(isHealthy){
            System.out.println("the menu calories is frendly;");
        }

        dishes.stream().filter(Dish::isVegetarian).findFirst().ifPresent(d-> System.out.println(d.getName()));

    }

}
