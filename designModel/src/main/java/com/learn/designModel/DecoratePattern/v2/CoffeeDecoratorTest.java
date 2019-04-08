package com.learn.designModel.DecoratePattern.v2;

/**
 * autor:liman
 * comment:
 */
public class CoffeeDecoratorTest {

    public static void main(String[] args) {
        //买一杯基础的咖啡
        Coffee coffee = new BaseCoffee();

        Coffee milkCoffee = new MilkCoffeeDecorator(coffee);

        Coffee milkAndSugarCoffee = new SugarCoffeeDecorator(milkCoffee);

        System.out.println("咖啡原料："+milkAndSugarCoffee.getMaterial()+"。售价："+milkAndSugarCoffee.getPrice());

    }

}
