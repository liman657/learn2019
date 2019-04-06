package com.learn.designModel.StrategyPattern.Pay;

/**
 * autor:liman
 * comment:
 */
public class WeChatPay extends PayMethod{
    @Override
    public String getName() {
        return "微信支付";
    }

    @Override
    protected double queryBalance(String uid) {
        return 200;
    }
}
