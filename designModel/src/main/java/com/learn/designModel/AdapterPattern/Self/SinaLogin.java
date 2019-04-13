package com.learn.designModel.AdapterPattern.Self;

import com.learn.designModel.AdapterPattern.ResultMsg;

/**
 * autor:liman
 * comment:
 */
public class SinaLogin implements ILoginAdapter{


    @Override
    public boolean support(Object adapter) {
        return adapter instanceof SinaLogin;
    }

    @Override
    public ResultMsg login(String username, String password) {
        ResultMsg resultMsg = new ResultMsg("200","使用新浪账号登录","登录成功");
        return resultMsg;
    }
}
