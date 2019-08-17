package com.learn.lambda.unionLambda;


import com.learn.lambda.common.Apple;
import com.learn.lambda.common.AppleContainer;

import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;

/**
 * author: liman
 * createtime: 2019/8/8
 */
public class UnionLambdaDemo {

    public static void main(String[] args) {
        List<Apple> appleList = AppleContainer.initAppleList();
//        sortByWeight(appleList);
//        sortByWeightDesc(appleList);
        System.out.println(appleList);
        List<Apple> result = complexPredicateAndOr(appleList);
        System.out.println(result);
    }

    public static void sortByWeight(List<Apple> appleList){
        appleList.sort(Comparator.comparing(Apple::getWeight));
        for(Apple apple:appleList){
            System.out.println(apple.getWeight());
        }
    }

    public static void sortByWeightDesc(List<Apple> appleList){
        appleList.sort(Comparator.comparing(Apple::getWeight).reversed());
    }

    public static void sortSameWeight(List<Apple> appleList){
        appleList.sort(Comparator.comparing(Apple::getWeight).reversed().thenComparing(Apple::getColor));
    }

    public static List<Apple> complexPredicateNegate(List<Apple> appleList){
        Predicate<Apple> predicate = apple -> "green".equals(apple.getColor());
        List<Apple> result = AppleContainer.filterApples(appleList, predicate.negate());
        return result;
    }

    public static List<Apple> complexPredicateAndOr(List<Apple> appleList){
        Predicate<Apple> predicate = apple -> "red".equals(apple.getColor());
        List<Apple> result = AppleContainer.filterApples(appleList,predicate.and(apple -> apple.getWeight()>=80).and(apple -> "well".equals(apple.getCategory())));
        return result;
    }

}