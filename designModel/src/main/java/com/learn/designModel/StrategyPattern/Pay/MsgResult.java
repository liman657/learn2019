package com.learn.designModel.StrategyPattern.Pay;

/**
 * autor:liman
 * comment:
 */
public class MsgResult {

    private int code;
    private Object data;
    private String msg;

    public MsgResult(int code, Object data, String msg) {
        this.code = code;
        this.data = data;
        this.msg = msg;
    }

    @Override
    public String toString() {
        return "支付结果{" +
                "code=" + code +
                ", data=" + data +
                ", msg='" + msg + '\'' +
                '}';
    }
}
