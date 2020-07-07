package com.learn.stream.create;

import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Random;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.joining;

/**
 * autor:liman
 * createtime:2020/7/7
 * comment: 自己总结Stream构建的几种方式
 */
@Slf4j
public class SelfCreateStreamDemo {

    private static int[] originalInt = new int[100];

    static{
        for(int i=0;i<100;i++){
            Random random = new Random();
            int num = random.nextInt(100);
            originalInt[i] = num;
        }
    }

    public static void main(String[] args) {
        boolean result = Arrays.stream(originalInt).anyMatch(i -> i > 0);
        int totalSum = Arrays.stream(originalInt).reduce(0, (i, j) -> i + j);
        Arrays.stream(originalInt).forEach(i->log.info("{}",i));
        int[] sortedResult = Arrays.stream(originalInt).sorted().toArray();

        log.info("has bigger num :{}",result);
        log.info("total sum : {}",totalSum);
        log.info("sorted result：{}",sortedResult);
    }

}
