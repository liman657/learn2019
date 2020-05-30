package com.learn.collector.stepin;

import com.learn.collector.common.Apple;
import com.learn.collector.common.AppleContainer;

import java.util.*;
import java.util.stream.Collectors;

/**
 * autor:liman
 * createtime:2019/9/17
 * comment: Collector初探
 */
public class CollectorStepIn {

    public static void main(String[] args) {
        List<Apple> apples = AppleContainer.initAppleList();

        Map<String, List<Apple>> collectorResult = groupByCollector(apples);
        System.out.println(collectorResult);
    }

    /**
     * 对这一堆苹果进行颜色的分类
     * @param apples
     * @return
     */
    public static Map<String, List<Apple>> groupAppleByColor(List<Apple> apples){
        Map<String,List<Apple>> map = new HashMap<>();
        for(Apple a:apples){
            List<Apple> list = map.get(a.getColor());
            if(null==list){
                list = new ArrayList<>();
                map.put(a.getColor(),list);
            }
            list.add(a);
        }
        return map;
    }

    /**
     * 用optional进行颜色的分类
     * @param apples
     * @return
     */
    private static Map<String,List<Apple>> groupByFunction(List<Apple> apples){
        Map<String,List<Apple>> map = new HashMap<>();
        apples.stream().forEach(a->{
            List<Apple> colorList=Optional.ofNullable(map.get(a.getColor())).orElseGet(()->{
                List<Apple> list = new ArrayList<>();
                map.put(a.getColor(),list);
                return list;
            });
            colorList.add(a);
        });
        return map;
    }

    /**
     * 使用collector进行颜色的分类
     * @param apples
     * @return
     */
    private static Map<String,List<Apple>> groupByCollector(List<Apple> apples){
        Map<String, List<Apple>> collect = apples.stream().collect(Collectors.groupingBy(Apple::getColor));
        return collect;
    }

}
