package com.learn.designModel.DecoratePattern.v1;

/**
 * autor:liman
 * comment:
 */
public class CoffeeWithSuger extends Coffee{

    @Override
    public String getMaterial() {
        return super.getMaterial()+",糖";
    }

    @Override
    public int getPrice() {
        return super.getPrice()+3;
    }
}
