package com.learn.stream.NumericStream;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2019/8/23
 * comment:numericStream 的实例
 */
public class NumericStreamDemo {

    public static void main(String[] args) {
        List<Dish> dishList = DishContainer.getDishList();
        List<Integer> numList = Arrays.asList(1, 2, 3, 4, 5, 6, 6, 9, 10, 7, 8);
//        Stream<Integer> stream;
//        stream.reduce()
    }

}
