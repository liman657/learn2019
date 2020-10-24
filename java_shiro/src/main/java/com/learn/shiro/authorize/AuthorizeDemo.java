package com.learn.shiro.authorize;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.apache.shiro.config.IniSecurityManagerFactory;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.util.Factory;

/**
 * autor:liman
 * createtime:2020/9/30
 * comment: 授权实例
 */
@Slf4j
public class AuthorizeDemo {

    private static Factory<SecurityManager> authorFactory = null;


    private static final String authorFactoryConfig = "classpath:shiro-role.ini";


    static{
        authorFactory = new IniSecurityManagerFactory(authorFactoryConfig);
    }

    public static void main(String[] args) {
        testAuthorFacory();
    }

    /**
     * 测试授权代码
     */
    public static void testAuthorFacory(){
        SecurityManager securityManager = authorFactory.getInstance();
        SecurityUtils.setSecurityManager(securityManager);
        Subject subject = SecurityUtils.getSubject();
        targetMethod();
    }

    @RequiresRoles("admin")
    public static void targetMethod(){
        String message = "this is target method";
        log.info("{}",message);
    }
}
