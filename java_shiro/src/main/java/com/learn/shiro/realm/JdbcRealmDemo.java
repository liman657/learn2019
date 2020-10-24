package com.learn.shiro.realm;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * autor:liman
 * createtime:2020/9/29
 * comment:基于数据库的realm
 */
@Slf4j
public class JdbcRealmDemo {

    public static void main(String[] args) {
        testJdbcRealm();
    }

    public static void testJdbcRealm(){
        Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-jdbc-realm.ini");
        SecurityManager securityManager = securityManagerFactory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("wang", "123");
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            log.error("登录异常，异常信息为:{}", e);
        }
        boolean isSuccess = subject.isAuthenticated();
        log.info("是否登录成功：{}", isSuccess);
        subject.logout();
    }

}
