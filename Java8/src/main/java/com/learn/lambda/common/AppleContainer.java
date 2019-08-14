package com.learn.lambda.common;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

/**
 * author: liman
 * createtime: 2019/8/8
 */
public class AppleContainer {

    private static List<Apple> appleList = new ArrayList<Apple>();

    /**
     * 初始化苹果容器
     * @return
     */
    public static List<Apple> initAppleList(){
        appleList = Arrays.asList(new Apple(150,"red","good"),
                new Apple(100,"green","good"),new Apple(80,"red","well"),
                new Apple(90,"red","excellent"));

        return appleList;
    }

    /**
     * 根据过滤器过滤苹果
     * @param appleList
     * @param predicate
     * @return
     */
    public static List<Apple> filterApples(List<Apple> appleList, Predicate<Apple> predicate){
        List<Apple> result = new ArrayList<>();
        for(Apple apple:appleList){
            if(predicate.test(apple)){
                result.add(apple);
            }
        }
        return result;
    }

}
