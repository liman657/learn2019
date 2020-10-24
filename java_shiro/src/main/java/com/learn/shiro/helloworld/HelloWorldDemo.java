package com.learn.shiro.helloworld;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.DefaultSecurityManager;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.realm.text.IniRealm;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * autor:liman
 * createtime:2020/9/29
 * comment:shiro认证 helloworld
 */
@Slf4j
public class HelloWorldDemo {

    public static void main(String[] args) {
//        testHelloworld();
//        testRealm();
        testMultiRealm();
    }

    /**
     * helloworld的测试
     */
    public static void testHelloworld() {
        //最新的构造方式
        //DefaultSecurityManager defaultSecurityManager = new DefaultSecurityManager();
        //IniRealm iniRealm = new IniRealm("classpath:shiro.ini");
        //defaultSecurityManager.setRealm(iniRealm);

        //过时的构造方式
        Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro.ini");
        SecurityManager securityManager = securityManagerFactory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhang", "123");
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            log.error("登录异常，异常信息为:{}", e);
        }
        boolean isSuccess = subject.isAuthenticated();
        log.info("是否登录成功：{}", isSuccess);
        subject.logout();
    }

    /**
     * 测试realm
     */
    public static void testRealm(){
        Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
        SecurityManager securityManager = securityManagerFactory.getInstance();

        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken("zhang", "123");
        try {
            subject.login(usernamePasswordToken);
        } catch (Exception e) {
            log.error("登录异常，异常信息为:{}", e);
        }
        boolean isSuccess = subject.isAuthenticated();
        log.info("是否登录成功：{}", isSuccess);
        subject.logout();
    }

    /**
     * 测试多个realm
     */
    public static void testMultiRealm(){
        Factory<SecurityManager> securityManagerFactory = new IniSecurityManagerFactory("classpath:shiro-realm.ini");
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
