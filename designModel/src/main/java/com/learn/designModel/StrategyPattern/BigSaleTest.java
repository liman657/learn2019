package com.learn.designModel.StrategyPattern;

/**
 * autor:liman
 * comment:
 */
public class BigSaleTest {

    public static void main(String[] args) {
//        PromotionStrategy promotionStrategy = new CashRepayPromotion();
//        BigSale bigSale = new BigSale(promotionStrategy);
//        bigSale.sale();

        PromotionStrategy cashrepay = PromotionStrategyFactory.getPromotionStrategy("UNIONBUY");
        BigSale bigSale1 = new BigSale(cashrepay);
        bigSale1.sale();
    }

}
