package com.learn.designModel.DecoratePattern.v1;

/**
 * autor:liman
 * comment:
 */
public class CoffeeWithSugarAndMilk extends CoffeeWithSugar {
    @Override
    public String getMaterial() {
        return super.getMaterial()+",奶泡";
    }

    @Override
    public int getPrice() {
        return super.getPrice()+5;
    }
}
