package com.learn.designModel.AdapterPattern;

/**
 * autor:liman
 * comment:
 */
public class LoginForQQAdapter implements LoginAdapter{
    @Override
    public boolean support(Object adapter) {
        return adapter instanceof LoginForQQAdapter;
    }

    @Override
    public ResultMsg login(String id, Object adapter) {
        return new ResultMsg("200","使用QQ进行第三方登录","QQ登录成功");
    }
}
