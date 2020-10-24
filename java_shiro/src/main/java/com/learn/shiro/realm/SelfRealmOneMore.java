package com.learn.shiro.realm;

import org.apache.shiro.authc.*;
import org.apache.shiro.realm.Realm;

/**
 * autor:liman
 * createtime:2020/9/29
 * comment:
 * 可以把 Realm 看成 DataSource，即安全数据源。如我们之前的 ini 配置方式将使用 org.apache.shiro.realm.text.IniRealm。
 */
public class SelfRealmOneMore implements Realm {
    public String getName() {
        return "test realm one";
    }

    public boolean supports(AuthenticationToken token) {
        return token instanceof UsernamePasswordToken;
    }

    /**
     * 身份验证的方法
     * @param token
     * @return
     * @throws AuthenticationException
     */
    public AuthenticationInfo getAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();//得到用户名
        String password = new String((char[])token.getCredentials()); //得到密码
        if(!"zhang".equalsIgnoreCase(username)){
            throw new UnknownAccountException();//如果用户名错误
        }
        if(!"123".equalsIgnoreCase(password)){
            throw new IncorrectCredentialsException();//密码错误
        }
        //如果身份认证验证成功，返回一个AuthenticationInfo实现；
        return new SimpleAuthenticationInfo("zhang@123.com", password, getName());
    }
}
