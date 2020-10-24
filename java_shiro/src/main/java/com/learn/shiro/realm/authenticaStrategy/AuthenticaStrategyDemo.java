package com.learn.shiro.realm.authenticaStrategy;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * autor:liman
 * createtime:2020/9/30
 * comment:
 * 认证策略实例
 * Authenticator 的职责是验证用户帐号，是 Shiro API 中身份验证核心的入口点，如果验证成功，
 *              将返回 AuthenticationInfo 验证信息；此信息中包含了身份及凭证
 *
 * SecurityManager 接口继承了 Authenticator
 * 另外还有一个 ModularRealmAuthenticator 实现，其委托给多个 Realm 进行验证，验证规则通过 AuthenticationStrategy 接口指定
 *
 * AuthenticationStrategy 接口指定验证账号的策略
 *              FirstSuccessfulStrategy：只要有一个 Realm 验证成功即可，只返回第一个 Realm 身份验证成功的认证信息，其他的忽略；
 *              AtLeastOneSuccessfulStrategy：只要有一个 Realm 验证成功即可，和 FirstSuccessfulStrategy 不同，
 *                  返回所有 Realm 身份验证成功的认证信息；
 *              AllSuccessfulStrategy：所有 Realm 验证成功才算成功，且返回所有 Realm 身份验证成功的认证信息，如果有一个失败就失败了。
 */
@Slf4j
public class AuthenticaStrategyDemo {

    private static Factory<SecurityManager> allSuccessFactory = null;

    private static Factory<SecurityManager> failFactory = null;

    private static final String shiroAllSuccessConfig = "classpath:shiro-authenticator-all-success.ini";

    private static final String shiroFailConfig = "classpath:shiro-authenticator-all-fail.ini";

    static{
        allSuccessFactory = new IniSecurityManagerFactory(shiroAllSuccessConfig);
        failFactory =new IniSecurityManagerFactory(shiroFailConfig);
    }

    public static void main(String[] args) {
        testAllSuccessStrategyWithSuccess();
    }

    /**
     *
     */
    public static void testAllSuccessStrategyWithSuccess(){
        SecurityManager securityManager = allSuccessFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken("zhang","123");
        subject.login(token);
        PrincipalCollection principals = subject.getPrincipals();
        log.info("认证的结果：{}",principals.asList().size());
    }



}
