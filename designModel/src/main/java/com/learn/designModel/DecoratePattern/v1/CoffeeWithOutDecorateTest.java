package com.learn.designModel.DecoratePattern.v1;

/**
 * autor:liman
 * comment:
 */
public class CoffeeWithOutDecorateTest {

    public static void main(String[] args) {
        Coffee coffee = new Coffee();
        System.out.println(coffee.getMaterial()+":总售价:"+coffee.getPrice());

        CoffeeWithSuger coffeeWithSuger = new CoffeeWithSuger();
        System.out.println(coffeeWithSuger.getMaterial()+":总售价:"+coffeeWithSuger.getPrice());

        CoffeeWithSugerAndMilk coffeeWithSugerAndMilk = new CoffeeWithSugerAndMilk();
        System.out.println(coffeeWithSugerAndMilk.getMaterial()+":总售价:"+coffeeWithSugerAndMilk.getPrice());
    }

}
