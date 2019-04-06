package com.learn.designModel.StrategyPattern.Pay;

/**
 * autor:liman
 * comment:
 */
public class PayTest {

    public static void main(String[] args) {
        Order order = new Order("1","201903310001",300);
        MsgResult msgResult = order.pay(PayStrategy.ALI_PAY);
        System.out.println(msgResult);
    }

}
