package com.learn.stream.middleoperation.filter;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * autor:liman
 * createtime:2019/8/23
 * comment:
 */
public class StreamFilterDemo {

    public static void main(String[] args) {
        List<Integer> numbers = Arrays.asList(1,2,3,4,5,6,6,7,7,1);

        List<Integer> collect = numbers.stream().filter(i -> i % 2 == 0).distinct().skip(2).collect(toList());
        System.out.println(collect);

//        List<Dish> dishList = DishContainer.getDishList();
//        List<Dish> result = dishList.stream().filter(Dish::isVegetarian).collect(toList());
//        System.out.println(result);

    }
}