package com.learn.designModel.DecoratePattern.v2;

/**
 * autor:liman
 * comment: 奶泡装饰器
 */
public class MilkDecorator extends Decorator {
    public MilkDecorator(Coffee coffee) {
        super(coffee);
    }

    @Override
    public String getMaterial() {
        return super.getMaterial()+",奶泡";
    }

    @Override
    public int getPrice() {
        return super.getPrice()+10;
    }
}
