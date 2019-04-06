package com.learn.designModel.StrategyPattern;

/**
 * autor:liman
 * comment: 返现
 */
public class CashRepayPromotion implements PromotionStrategy {
    @Override
    public void promotion() {
        System.out.println("满1000返300");
    }
}
