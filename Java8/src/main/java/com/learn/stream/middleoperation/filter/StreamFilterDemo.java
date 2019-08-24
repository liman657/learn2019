package com.learn.stream.middleoperation.filter;

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
        List<Integer> list = Arrays.asList(1,2,3,4,5,6,6,7,7,1);

        Stream<Integer> dataStream = list.stream();
//        List<Integer> collect = dataStream.filter(i -> i % 2 == 0).collect(toList());
//        System.out.println(collect);

        System.out.println(dataStream.distinct().collect(toList()));
    }

}
