package com.learn.designModel.StrategyPattern.Pay;

/**
 * autor:liman
 * comment:抽象的支付方式
 */
public abstract class PayMethod {

    public abstract String getName();

    protected abstract double queryBalance(String uid);

    /**
     * 支付
     * @param uid
     * @param amount
     * @return
     */
    protected MsgResult pay(String uid,double amount){
        if(queryBalance(uid)<amount){
            return new MsgResult(500,"支付失败","余额不足");
        }
        return new MsgResult(200,"支付成功","支付金额"+amount);
    }

}
