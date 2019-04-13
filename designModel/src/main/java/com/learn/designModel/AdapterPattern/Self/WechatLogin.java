package com.learn.designModel.AdapterPattern.Self;

import com.learn.designModel.AdapterPattern.ResultMsg;

/**
 * autor:liman
 * comment:
 */
public class WechatLogin implements ILoginAdapter{

    public void loginByWechat(String username,String password){
        System.out.println("现在采用微信号登录");
    }

    @Override
    public boolean support(Object adapter) {
        return adapter instanceof WechatLogin;
    }

    @Override
    public ResultMsg login(String username, String password) {
        ResultMsg resultMsg = new ResultMsg("200","使用微信账号登录","登录成功");
        return resultMsg;
    }
}
