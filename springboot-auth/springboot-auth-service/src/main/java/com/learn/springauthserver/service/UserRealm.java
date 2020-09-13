package com.learn.springauthserver.service;

import com.learn.springauthmodel.entity.User;
import com.learn.springauthmodel.mapper.UserMapper;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.management.RuntimeMBeanException;

/**
 * autor:liman
 * createtime:2019/12/23
 * comment: 自定义的用户认证和授权realm
 */
@Component
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private UserMapper userMapper;

    /**
     * 用户授权部分
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        return null;
    }

    /**
     * 用户认证部分
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        final String userName = token.getUsername();
        final String password = String.valueOf(token.getPassword());
        log.info("--shiro 用户身份认证，当前用户名和密码为:{},{}",userName,password);

        User user=userMapper.selectByUserName(userName);
        if(user==null){
            throw new RuntimeException("当前用户不存在");
        }
        if(!password.equals(user.getPassword())){
            throw new RuntimeException("用户名和密码不匹配");
        }

        user.setPassword(null);

        //认证成功之后，返回一个SimpleAuthenticationInfo
        return new SimpleAuthenticationInfo(user,password,getName());
    }
}
