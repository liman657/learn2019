package com.learn.stream.reduce;

import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2019/8/26
 * comment:stream reduce实例
 */
public class StreamReduceDemo {

    public static void main(String[] args) {
        //元素求和
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Integer result = stream.reduce(0, (i, j) -> i + j);
        System.out.println(result);

        //最大值和最小值
        Stream<Integer> numStream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Optional<Integer> maxNum = numStream.reduce(Integer::max);
        System.out.println(maxNum.get());

        Stream<Integer> minStream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Optional<Integer> minNum = minStream.reduce(Integer::min);
        System.out.println(minNum.get());

        //找出其中最大的
        Stream<Integer> maxStream = Arrays.stream(new Integer[]{1, 2, 3, 4, 99, 5, 6, 7});
        maxStream.reduce((i, j) -> i < j ? j : i).ifPresent(System.out::println);
    }
}