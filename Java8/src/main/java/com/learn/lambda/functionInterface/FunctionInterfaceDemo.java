package com.learn.lambda.functionInterface;

import com.learn.lambda.common.Apple;
import com.learn.lambda.common.AppleContainer;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;

/**
 * autor:liman
 * createtime:2019/8/15
 * comment:
 */
public class FunctionInterfaceDemo {

    public static void main(String[] args) {
        List<Apple> appleList = AppleContainer.initAppleList();
        testPredicate(appleList);
        testForeachByConsumer(appleList);
        testMapbyFunction(appleList);
    }

    public static void testPredicate(List<Apple> appleList){
        System.out.println("===============test predicate===============");
        Predicate<Apple> predicate = apple -> apple.getColor().equals("green");
        List<Apple> result = filterByPredicate(appleList,predicate);
        System.out.println(result);
    }

    public static void testForeachByConsumer(List<Apple> apples){
        System.out.println("===============test consumer===============");
        Consumer<Apple> consumer = apple -> System.out.println(apple.getWeight());
        forEach(apples,consumer);
    }

    public static void testMapbyFunction(List<Apple> apples){
        System.out.println("===============test function===============");
        Function<Apple,String> func= apple->apple.getColor();
        List<String> appleColors = map(apples, func);
        System.out.println(appleColors);
    }

    public static <T> List<T> filterByPredicate(List<T> list,Predicate<T> p){
        List<T> result = new ArrayList<>();
        for(T s:list){
            if(p.test(s)){
                result.add(s);
            }
        }
        return result;
    }

    /**
     * consumer
     * @param list
     * @param c
     * @param <T>
     */
    public static <T> void forEach(List<T> list,Consumer<T> c){
        for(T t:list){
            c.accept(t);
        }
    }

    /**
     * function
     * @param list
     * @param function
     * @param <T>
     * @param <R>
     * @return
     */
    public static <T,R> List<R> map(List<T> list,Function<T,R> function){
        List<R> result = new ArrayList<>();
        for(T s:list){
            result.add(function.apply(s));
        }
        return result;
    }
}