package com.learn.designModel.DecoratePattern.v2;

/**
 * autor:liman
 * comment:
 */
public class CoffeeDecoratorTest {

    public static void main(String[] args) {
        //买一杯基础的咖啡
        Coffee coffee = new BaseCoffee();
        System.out.println("咖啡原料："+coffee.getMaterial()+"。售价："+coffee.getPrice());

        //给咖啡只加奶泡
        Coffee milkCoffee = new MilkDecorator(coffee);
        System.out.println("咖啡原料："+milkCoffee.getMaterial()+"。售价："+milkCoffee.getPrice());

        //给coffee同时加入奶泡和糖
        Coffee milkAndSugarCoffee = new SugarDecorator(new MilkDecorator(coffee));
        System.out.println("咖啡原料："+milkAndSugarCoffee.getMaterial()+"。售价："+milkAndSugarCoffee.getPrice());
    }
}