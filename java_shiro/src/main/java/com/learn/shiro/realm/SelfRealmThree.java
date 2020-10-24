package com.learn.shiro.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * autor:liman
 * createtime:2020/9/29
 * comment:
 */
@Slf4j
public class SelfRealmThree implements Realm {
    public String getName() {
        return "test realm three";
    }

    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();//得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码
        if(!"zhang@163.com".equalsIgnoreCase(username)){
            throw new UnknownAccountException();//如果用户名错误
        }
        if(!"123".equalsIgnoreCase(password)){
            throw new IncorrectCredentialsException();//密码错误
        }
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo(username, password, getName());
    }
}
