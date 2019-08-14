package com.learn.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * createtime:2019/8/4
 * comment:
 */
public class AppleList {

    private static List<Apple> apples = new ArrayList<>();

    public static List<Apple> initApples(){
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

}
