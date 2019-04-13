package com.learn.designModel.AdapterPattern.Self;

import com.learn.designModel.AdapterPattern.ResultMsg;

/**
 * autor:liman
 * comment:已经正常运行的登录注册逻辑
 */
public class SiginService {

    public void login(String username,String password){
        System.out.println("系统已经正常运行的登录：校验用户名和密码"+username+":"
                +password+new ResultMsg("200",username,"登录成功"));
    }

    public void register(String username,String password){
        System.out.println("系统已经正常运行的注册：校验用户名和密码"+username+":"
                +password+new ResultMsg("200",username,"注册成功"));
    }
}
