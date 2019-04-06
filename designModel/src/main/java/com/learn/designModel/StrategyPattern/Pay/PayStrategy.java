package com.learn.designModel.StrategyPattern.Pay;

import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * comment:
 */
public class PayStrategy {

    public static final String ALI_PAY = "Alipay";
    public static final String WECHAT_PAY = "Wechatpay";
    public static final String DEFAULT_PAY = ALI_PAY;

    private static Map<String, PayMethod> PAY_METHOD = new HashMap<>();

    static {
        PAY_METHOD.put(ALI_PAY, new AliPay());
        PAY_METHOD.put(WECHAT_PAY, new WeChatPay());
    }

    public static PayMethod getPayMethod(String payKey) {
        if (PAY_METHOD.containsKey(payKey)) {
            return PAY_METHOD.get(payKey);
        }else{
            return PAY_METHOD.get(DEFAULT_PAY);
        }
    }

}
