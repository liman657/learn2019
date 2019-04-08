package com.learn.designModel.DecoratePattern.v2;

/**
 * autor:liman
 * mobilNo:15528212893
 * mail:657271181@qq.com
 * comment:
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
