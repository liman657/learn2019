package com.learn.stream.middleoperation.map;

import com.learn.stream.common.Dish;
import com.learn.stream.common.DishContainer;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * autor:liman
 * createtime:2019/8/23
 * comment:
 */
public class StreamMapDemo {

    public static void main(String[] args) {
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,6,7,7,1);
        Stream<Integer> dataStream = list.stream();

        List<Integer> result = dataStream.map(i -> i * 2).collect(toList());
        System.out.println(result);

//        List<Dish> dishList = DishContainer.getDishList();
//        dishList.stream().flatMap();
        String[] strings = {"hello","world"};

        //{h,e,l,l,o,w,o,r,l,d}
        Stream<String[]> stream = Arrays.asList(strings).stream().map(s -> s.split(""));
        //List<String> numStream = stream.flatMap(Arrays::stream) 这个会变成 H,e,l,o,w,r,d
        List<String> numStream = stream.flatMap(Arrays::stream).distinct().collect(toList());
        System.out.println(numStream);

    }

}
