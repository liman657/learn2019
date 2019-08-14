package com.learn.lambda.chapter03;

import com.learn.lambda.Apple;
import com.learn.lambda.AppleList;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * autor:liman
 * createtime:2019/8/5
 * comment: lambda表达式的使用
 */
public class LambdaUsage {

    private static List<Apple> apples = AppleList.initApples();

    private static List<Apple> filter(List<Apple> source, Predicate<Apple> predicate) {
        List<Apple> result = new ArrayList<>();
        for (Apple a : source) {
            if (predicate.test(a))
                result.add(a);
        }
        return result;

    }

    public static void main(String[] args) {
        Predicate<Apple> p = (Apple a) -> a.getColor().equals("red");

        List<Apple> filter = filter(apples, p);

        System.out.println(filter);

    }
}