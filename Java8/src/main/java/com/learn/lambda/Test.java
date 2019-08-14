package com.learn.lambda;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * comment:
 */
public class Test {

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

    public static List<Apple> filteGreenApple(List<Apple> apples){
        List<Apple> result = new ArrayList<>();
        for(Apple apple:apples){
            if("green".equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }


    /**
     * 各种过滤方法
     * @param apples
     * @param color
     * @return
     */
    public static List<Apple> filterApplesByColor(List<Apple> apples,String color){
        List<Apple> result = new ArrayList<>();
        for(Apple apple:apples){
            if(color.equals(apple.getColor())){
                result.add(apple);
            }
        }
        return result;
    }

    public static List<Apple> filterByWeight(List<Apple> apples,int weight){
        List<Apple> result = new ArrayList<>();
        for(Apple apple:apples){
            if(apple.getWeight()>weight){
                result.add(apple);
            }
        }
        return result;
    }

    public static void main(String[] args) {
        List<Apple> apples = initApples();
        List<Apple> redApples = filterApplesByColor(apples, "green");
        System.out.println(redApples);

//        List<Apple> appleOverWeight = filterByWeight(apples, 50);
//        System.out.println(appleOverWeight);
    }

}
