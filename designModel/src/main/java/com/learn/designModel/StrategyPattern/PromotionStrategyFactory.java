package com.learn.designModel.StrategyPattern;

import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * comment:
 */
public class PromotionStrategyFactory {

    private static Map<String,PromotionStrategy> PROMOTION_MAP = new HashMap<>();

    static{
        PROMOTION_MAP.put(Promotionkey.CASHREPAY,new CashRepayPromotion());
        PROMOTION_MAP.put(Promotionkey.DISCOUNT,new DiscountPromotion());
        PROMOTION_MAP.put(Promotionkey.UNIONBUY,new UnionDiscountPromotion());

    }

    public static PromotionStrategy getPromotionStrategy(String promotionKey){
        return PROMOTION_MAP.get(promotionKey);
    }


    private PromotionStrategyFactory(){}


    private interface Promotionkey{
        public String CASHREPAY="CASHREPAY";
        public String DISCOUNT="DISCOUNT";
        public String UNIONBUY="UNIONBUY";
    }

}
