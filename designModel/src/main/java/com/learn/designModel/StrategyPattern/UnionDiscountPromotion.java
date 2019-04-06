package com.learn.designModel.StrategyPattern;

/**
 * autor:liman
 * comment: 团购满减
 */
public class UnionDiscountPromotion implements PromotionStrategy {
    @Override
    public void promotion() {
        System.out.println("团购减免的促销");
    }
}
