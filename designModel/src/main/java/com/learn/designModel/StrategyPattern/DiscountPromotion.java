package com.learn.designModel.StrategyPattern;

/**
 * autor:liman
 * comment:折扣优惠
 */
public class DiscountPromotion implements PromotionStrategy {
    @Override
    public void promotion() {
        System.out.println("在售商品打8折");
    }
}
