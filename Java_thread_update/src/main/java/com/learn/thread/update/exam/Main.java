package com.learn.thread.update.exam;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.Scanner;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

/**
 * autor:liman
 * createtime:2020/7/2
 * comment:
 */
public class Main {

    public static void main(String[] args) {
//        try {
//            Scanner sc = new Scanner(System.in);
//            String nums = sc.next();
//            String[] spileNums = nums.split(",");
//            List<Integer> collect = Stream.of(spileNums).map(n -> Integer.valueOf(n)).sorted().collect(Collectors.toList());
//            int result = -1;
//            int next = -1;
//            for (int i = 0; i < collect.size() - 1; i++) {
//                next = collect.get(i + 1);
//                if (next - i > 1) {
//                    result = i+1;
//                    break;
//                }
//            }
//            System.out.println(result);
//        } catch (Exception e) {
//            System.out.println(0);
//            return;
//        }

        sumFindMethod();
    }

    private static void betterMethod(){
        try {
            Scanner sc = new Scanner(System.in);
            String nums = sc.next();
            String[] spileNums = nums.split(",");
            List<Integer> collect = Stream.of(spileNums).map(n -> Integer.valueOf(n)).sorted().collect(Collectors.toList());
            int index = Stream.of(spileNums).map(n -> Integer.valueOf(n)).sorted().filter(i -> collect.indexOf(i)!=i).findFirst().get();
            System.out.println(index-1);
        } catch (Exception e) {
            System.out.println(0);
            return;
        }
    }

    private static void sumFindMethod(){
        try {
            Scanner sc = new Scanner(System.in);
            String nums = sc.next();
            String[] spileNums = nums.split(",");
            Integer realSum = Stream.of(spileNums).map(n -> Integer.valueOf(n)).reduce(0, (i, j) -> i + j);
            Integer allSum = IntStream.rangeClosed(1,spileNums.length).sum();
            System.out.println(allSum-realSum);
        } catch (Exception e) {
            System.out.println(0);
            return;
        }
    }

}
