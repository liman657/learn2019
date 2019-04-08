package com.learn.designModel.DecoratePattern.v2;

/**
 * autor:liman
 * comment:
 */
public class BaseCoffee extends Coffee {
    @Override
    public String getMaterial() {
        return "咖啡豆,"+"白开水";
    }

    @Override
    public int getPrice() {
        return 20;
    }
}
