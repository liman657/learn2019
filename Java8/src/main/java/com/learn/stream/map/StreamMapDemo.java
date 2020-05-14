package com.learn.stream.map;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2020/5/14
 * comment:
 */
@Slf4j
public class StreamMapDemo {

    public static void main(String[] args) {
        List<Integer> numList = Arrays.asList(1,2,3,4,5,6,6,9,10,7,8);

        List<Integer> mapResult = numList.stream().map(i -> i * 2).collect(Collectors.toList());
        log.info("map result:{}",mapResult);

        List<Dish> dishList = DishContainer.getDishList();
        List<String> dishNames = dishList.stream().map(dish -> dish.getName()).collect(Collectors.toList());
        log.info("dish map names:{}",dishNames);
        
        //flatMap
        String[] words = {"hello","world"};
        //{{h,e,l,l,o},{w,o,r,l,d}}
        Stream<String[]> stream = Arrays.stream(words).map(word -> word.split(""));
        //flatmap之后变成 {h,e,l,l,o,w,o,r,l,d}
        List<String> characterList = stream.flatMap(s -> Arrays.stream(s)).distinct().collect(Collectors.toList());
        log.info("characterList result:{}",characterList);
    }

}
