package com.learn.lambda.MethodReference;

import com.learn.lambda.common.Apple;
import com.learn.lambda.common.AppleContainer;

import java.util.Comparator;
import java.util.List;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

/**
 * author: liman
 * createtime: 2019/8/8
 */
public class MethodReferenceDemo {

    public static void main(String[] args) {
        List<Apple> appleList = AppleContainer.initAppleList();

        testSupplier();
        testFunction();
        testBiFunction();
        testObjectMethodReference();
        testStaticMethodReference();
        testArrayMethodReference();
    }

    public static void testSupplier(){
        System.out.println("==========test supplier===============");
        //相当于实现了一个Supplier的实现类
        Supplier<Apple> appleSupplier = ()->{return new Apple();};
        Apple apple = appleSupplier.get();
        System.out.println(apple);

        Supplier<Apple> appleSupplierReference = Apple::new;
        apple = appleSupplierReference.get();
        System.out.println(apple);
    }

    public static void testFunction(){
        System.out.println("==========test function===============");
        Function<Integer,Apple> functionApple = (weight)->new Apple(weight);
        Apple weightApple = functionApple.apply(135);
        System.out.println(weightApple);

        Function<Integer,Apple> integerAppleFunction = Apple::new;
        Apple bigApple = integerAppleFunction.apply(160);
        System.out.println(bigApple);
    }

    public static void testBiFunction(){
        System.out.println("==========test BiFunction===============");
        BiFunction<Integer,String,Apple> biFunction = (weight,color)->new Apple(weight,color);
        Apple biFunctionApple = biFunction.apply(120, "green");
        System.out.println(biFunctionApple);

        BiFunction<Integer,String,Apple> biFunctionReference = Apple::new;
        Apple biFunctionReferenceApple = biFunctionReference.apply(132, "red");
        System.out.println(biFunctionReferenceApple);
    }

    public static void testObjectMethodReference(){
        System.out.println("==========test staticMethodReference===============");
        Consumer<String> consumer = (str)-> System.out.println(str);
        consumer.accept("test Consumer lambda");

        Consumer<String> staticConsumer = System.out::println;
        staticConsumer.accept("test consumer method reference");
    }

    public static void testStaticMethodReference(){
        System.out.println("==========test StaticMethodReference===============");
        Comparator<Integer> com = (a,b)->a.compareTo(b);
        int compare = com.compare(2, 3);
        System.out.println(compare);

        Comparator<Integer> compStaticLambda = (a,b)->Integer.compare(a,b);
        int compStaticResult = compStaticLambda.compare(5, 6);
        System.out.println(compStaticResult);

        Comparator<Integer> compStaticMethodReference = Integer::compare;
        int compStaticMethodReferenceResult = compStaticMethodReference.compare(10,8);
        System.out.println(compStaticMethodReferenceResult);
    }

    public static void testArrayMethodReference(){
        System.out.println("==========test ArrayMethodReference===============");
        Function<Integer,String[]> fuStringArrayLambda = (a)->new String[a];
        String[] stringArrayLambda = fuStringArrayLambda.apply(10);
        System.out.println(stringArrayLambda.length);

        Function<Integer,String[]> fuStringArrayMethodReference = String[]::new;
        String[] stringArrayMethodReference = fuStringArrayMethodReference.apply(100);
        System.out.println(stringArrayMethodReference.length);

    }

}
