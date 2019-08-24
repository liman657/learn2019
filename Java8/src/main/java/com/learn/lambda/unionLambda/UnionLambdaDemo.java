package com.learn.lambda.unionLambda;


import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.learn.lambda.common.Apple;
import com.learn.lambda.common.AppleContainer;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * author: liman
 * createtime: 2019/8/8
 * comment:复合lambda表达式
 */
public class UnionLambdaDemo {

    public static void main(String[] args) {
        List<Apple> appleList = AppleContainer.initAppleList();
//        compareReverse(appleList);
//        compareWithThen(appleList);
        predicateCombine(appleList);

        functionCombine(1);
    }

    public static void compareReverse(List<Apple> apples){
        System.out.println("原始数据:"+apples);
        Comparator<Apple> weightComp = Comparator.comparing(Apple::getWeight);
        Collections.sort(apples,weightComp);
        System.out.println("正常排序:"+apples);
        Collections.sort(apples,weightComp.reversed());
        System.out.println("逆序排序:"+apples);
    }

    public static void compareWithThen(List<Apple> apples){
        Comparator<Apple> weightComp = Comparator.comparing(Apple::getWeight);
        Collections.sort(apples,weightComp.reversed().thenComparing(Apple::getColor));
        System.out.println(apples);
    }

    public static void predicateCombine(List<Apple> apples){
        Predicate<Apple> applePredicate = apple->apple.getColor().equals("red");
//        List<Apple> notRedApple = filterApple(apples, applePredicate.negate());
//        System.out.println(notRedApple);

        List<Apple> bigApple = filterApple(apples, applePredicate.and(apple -> apple.getWeight() > 100).or(apple -> apple.getColor().equals("green")));
        System.out.println(bigApple);
    }


    public static List<Apple> filterApple(List<Apple> apples,Predicate<Apple> applePredicate){
        List<Apple> result = new ArrayList<>();
        for (Apple apple:apples){
            if(applePredicate.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    public static void functionCombine(int param){
        Function<Integer,Integer> f = x->x+1;
        Function<Integer,Integer> g = x->x*2;
        Function<Integer,Integer> r = f.andThen(g);
        int andThenResult = r.apply(param);
        System.out.println(andThenResult);

        Function<Integer,Integer> nr = f.compose(g);
        int composeResult = nr.apply(param);
        System.out.println(composeResult);
    }
}