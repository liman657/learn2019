package com.learn.designModel.StrategyPattern.Pay;

/**
 * autor:liman
 * comment:
 */
public class AliPay extends PayMethod {
    @Override
    public String getName() {
        return "支付宝支付";
    }

    @Override
    protected double queryBalance(String uid) {
        return 900;
    }
}
