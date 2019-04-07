package com.learn.designModel.AdapterPattern;

/**
 * autor:liman
 * comment:
 */
public class SiginService {

    /**
     * 注册方法
     * @param username
     * @param password
     * @return
     */
    public ResultMsg register(String username,String password){
        return new ResultMsg("200",username,"注册成功");
    }

    /**
     * 登录方法
     * @param username
     * @param password
     * @return
     */
    public ResultMsg login(String username,String password){
        return new ResultMsg("200",username,"登录成功");
    }


}
