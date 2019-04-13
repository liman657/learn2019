package com.learn.designModel.AdapterPattern.Self;

import com.learn.designModel.AdapterPattern.ResultMsg;

/**
 * autor:liman
 * comment:
 */
public class AaliLogin implements ILoginAdapter{


    @Override
    public boolean support(Object adapter) {
        return adapter instanceof AaliLogin;
    }

    @Override
    public ResultMsg login(String username, String password) {
        ResultMsg resultMsg = new ResultMsg("200","使用支付宝账号登录","登录成功");
        return resultMsg;
    }
}
