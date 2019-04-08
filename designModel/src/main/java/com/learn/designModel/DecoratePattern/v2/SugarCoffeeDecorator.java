package com.learn.designModel.DecoratePattern.v2;

/**
 * autor:liman
 * comment:
 */
public class SugarCoffeeDecorator extends CoffeeDecorator {
    public SugarCoffeeDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getMaterial() {
        return super.getMaterial()+",糖";
    }

    @Override
    public int getPrice() {
        return super.getPrice()+5;
    }
}
