package com.learn.lambda;

import com.learn.lambda.chapter02.stragegy.AppleFilter;
import com.learn.lambda.chapter02.stragegy.ColorFilter;
import com.learn.lambda.chapter02.stragegy.WeightFilter;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

/**
 * autor:liman
 * createtime:2019/8/3
 * comment:
 */
public class FilterTest {

    private static List<Apple> apples = new ArrayList<>();

    private static List<Apple> initApples(){
        Apple apple01 = new Apple(50,"red",true);
        Apple apple02 = new Apple(30,"red",false);
        Apple apple03 = new Apple(100,"green",true);
        Apple apple04 = new Apple(80,"red",false);
        Apple apple05 = new Apple(20,"green",true);

        apples.add(apple01);
        apples.add(apple02);
        apples.add(apple03);
        apples.add(apple04);
        apples.add(apple05);

        return apples;
    }

    public static List<Apple> filterApples(List<Apple> apples, AppleFilter appleFilter){
        List<Apple> result = new ArrayList<>();
        for(Apple apple:apples){
            if(appleFilter.filter(apple)){
                result.add(apple);
            }
        }
        return result;
    }

    public static void main(String[] args) {

        List<Apple> apples = initApples();
        //根据颜色过滤
        List<Apple> redApples = filterApples(apples, new ColorFilter("red"));
        System.out.println(redApples);

        //根据重量过滤
        List<Apple> bigApples = filterApples(apples, new WeightFilter(50, true));
        System.out.println(bigApples);

        List<Apple> redApplesLambda = filterApples(apples,(Apple apple)->"red".equals(apple.getColor()));
        System.out.println(redApplesLambda);

        List<Apple> lambdaFilter = filterApples(apples,apple->apple.getWeight()==50);
        System.out.println(lambdaFilter);
    }
}