package com.learn.lambda.chapter03;

import com.learn.lambda.Apple;
import com.learn.lambda.AppleList;

import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * autor:liman
 * createtime:2019/8/4
 * comment: lambda 表达式的实例
 */
public class LambdaExpress {

    public static void main(String[] args) {
        Comparator<Apple> byColor = new Comparator<Apple>() {
            @Override
            public int compare(Apple o1, Apple o2) {
                return o1.getColor().compareTo(o2.getColor());
//                return o1.getWeight().compareTo(o2.getWeight());
            }
        };
//
        List<Apple> appleList = AppleList.initApples();
//        appleList.sort(byColor);
//        System.out.println(appleList);


        Comparator<Apple> byColor2 = (o1,o2)->{return o2.getColor().compareTo(o1.getColor());};

//        Comparator<Apple> byWeight = (o1,o2)->o1.getWeight().compare(o2.getWeight())

        appleList.sort(byColor2);
        System.out.println(appleList);

        Function<String,Integer> flambda = s->s.length();

        Predicate<Apple> p = a->a.getColor().equals("green");




    }

}
