package com.learn.stream.middleoperation.find;

import java.util.Arrays;
import java.util.Optional;
import java.util.function.Predicate;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2019/8/26
 * comment:
 */
public class StreamFindDemo {

    public static void main(String[] args) {
        Stream<Integer> stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});

        Optional<Integer> result = stream.filter(i -> i % 2 == 0).findAny();
        System.out.println(result.get());

        stream = Arrays.stream(new Integer[]{1, 2, 3, 4, 5, 6, 7});
        Optional<Integer> first = stream.filter(i -> i % 2 == 1).findFirst();
        System.out.println(first.get());
    }

    public static int find(Integer[] values,int defaultValue,Predicate<Integer> predicate){
        for(int i:values){
            if(predicate.test(i)){

            }
        }
        return 0;
    }

}
