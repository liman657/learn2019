package com.learn.designModel.DecoratePattern.v1;

/**
 * autor:liman
 * mobilNo:15528212893
 * mail:657271181@qq.com
 * comment:
 */
public class CoffeeWithSugerAndMilk extends CoffeeWithSuger {
    @Override
    public String getMaterial() {
        return super.getMaterial()+",奶泡";
    }

    @Override
    public int getPrice() {
        return super.getPrice()+5;
    }
}
