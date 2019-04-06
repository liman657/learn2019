package com.learn.designModel.StrategyPattern;

/**
 * autor:liman
 * comment:
 */
public class BigSale {

    private PromotionStrategy promotionStrategy;

    public BigSale(PromotionStrategy promotionStrategy){
        this.promotionStrategy = promotionStrategy;
    }

    public void sale(){
        promotionStrategy.promotion();
    }


}
