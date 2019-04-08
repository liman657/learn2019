package com.learn.designModel.DecoratePattern.v2;

/**
 * autor:liman
 * comment:
 */
public class CoffeeDecorator extends Coffee{

    private Coffee coffee;

    public CoffeeDecorator(Coffee coffee) {
        this.coffee = coffee;
    }

    @Override
    public String getMaterial() {
        return this.coffee.getMaterial();
    }

    @Override
    public int getPrice() {
        return this.coffee.getPrice();
    }
}
