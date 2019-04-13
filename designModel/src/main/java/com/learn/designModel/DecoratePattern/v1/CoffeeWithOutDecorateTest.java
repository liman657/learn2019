package com.learn.designModel.DecoratePattern.v1;

/**
 * autor:liman
 * comment:
 */
public class CoffeeWithOutDecorateTest {

    public static void main(String[] args) {
        Coffee coffee = new Coffee();
        System.out.println(coffee.getMaterial()+":总售价:"+coffee.getPrice());

        CoffeeWithSugar coffeeWithSugar = new CoffeeWithSugar();
        System.out.println(coffeeWithSugar.getMaterial()+":总售价:"+ coffeeWithSugar.getPrice());

        CoffeeWithSugarAndMilk coffeeWithSugerAndMilk = new CoffeeWithSugarAndMilk();
        System.out.println(coffeeWithSugerAndMilk.getMaterial()+":总售价:"+coffeeWithSugerAndMilk.getPrice());
    }
}
