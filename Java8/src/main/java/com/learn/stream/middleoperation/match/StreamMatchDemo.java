package com.learn.stream.middleoperation.match;

import java.util.Arrays;
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

        boolean noneMatchBoolean = stream.noneMatch(i -> i > 10);
        System.out.println(noneMatchBoolean);
    }

}
