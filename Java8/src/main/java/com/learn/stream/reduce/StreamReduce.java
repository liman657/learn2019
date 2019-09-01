package com.learn.stream.reduce;

import java.util.Arrays;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2019/8/26
 * comment:
 */
public class StreamReduce {

    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Integer result = stream.reduce(0, (i, j) -> i + j);
        System.out.println(result);
    }

}
