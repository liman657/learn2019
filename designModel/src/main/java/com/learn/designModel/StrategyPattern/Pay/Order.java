package com.learn.designModel.StrategyPattern.Pay;

/**
 * autor:liman
 * comment:
 */
public class Order {

    private String uid;
    private String orderId;
    private double amount;

    public Order(String uid, String orderId, double amount) {
        this.uid = uid;
        this.orderId = orderId;
        this.amount = amount;
    }

    /**
     * 完成订单的支付
     * @param payMethod
     * @return
     */
    public MsgResult pay(String payMethod){
        PayMethod payStrategy = PayStrategy.getPayMethod(payMethod);
        System.out.println("开始使用"+payStrategy.getName()+"进行付款");
        System.out.println("本次交易金额:"+amount+"开始扣款");
        return payStrategy.pay(uid,amount);
    }
}
