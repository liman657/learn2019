package com.liman.learn.pmp.config;

import com.liman.learn.pmp.shiro.UserRealm;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.mgt.SecurityManager;
import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/2
 * comment: shiro 的配置类
 */
@Configuration
@Slf4j
public class ShiroConfig {

    @Bean
    public SecurityManager securityManager(UserRealm userRealm){
        DefaultWebSecurityManager securityManager=new DefaultWebSecurityManager();
        securityManager.setRealm(userRealm);
        securityManager.setRememberMeManager(null);
        return securityManager;
    }

    /**
     * shiroFilterFactoryBean是shiro的一个认证过滤器链，这个过滤器中就会配置认证失败的跳转页面，需要认证的路径等等等
     * @param securityManager
     * @return
     */
    @Bean("shiroFilter")
    public ShiroFilterFactoryBean shiroFilter(SecurityManager securityManager){
        ShiroFilterFactoryBean shiroFilterFactoryBean = new ShiroFilterFactoryBean();
        shiroFilterFactoryBean.setSecurityManager(securityManager);

        shiroFilterFactoryBean.setLoginUrl("/login.html");//配置如果没有登录，则会跳转到的页面
        shiroFilterFactoryBean.setUnauthorizedUrl("/403.html");//配置如果没有认证，则会跳转到的目标页面

        //过滤器调用链配置，配置指定的url，以指定的方式进行访问
        /**
         * key,value = url,访问的方式
         * 访问的方式有如下几种：
         */
        Map<String,String> filterMap = new LinkedHashMap<>();
//        filterMap.put("/swagger/**", "anon");
//        filterMap.put("/swagger-ui.html", "anon");
//        filterMap.put("/webjars/**", "anon");
//        filterMap.put("/swagger-resources/**", "anon");

        /**
         * 如下url可以以anon 匿名方式（不用登录）访问
         */
        filterMap.put("/statics/**", "anon");
        filterMap.put("/login.html", "anon");
        filterMap.put("/login", "anon");
        filterMap.put("/favicon.ico", "anon");
        filterMap.put("/captcha.jpg", "anon");

        /**
         * 其他请求路径，需要登录认证之后才能访问
         */
        filterMap.put("/**","authc");

        shiroFilterFactoryBean.setFilterChainDefinitionMap(filterMap);
        return shiroFilterFactoryBean;
    }

    //关于Shiro的Bean生命周期的管理
    @Bean("lifecycleBeanPostProcessor")
    public LifecycleBeanPostProcessor lifecycleBeanPostProcessor(){
        return new LifecycleBeanPostProcessor();
    }

    /**
     * 授权属性的管理，用的很少，在权限注解里头有点用
     * @param securityManager
     * @return
     */
    @Bean
    public AuthorizationAttributeSourceAdvisor authorizationAttributeSourceAdvisor(SecurityManager securityManager){
        AuthorizationAttributeSourceAdvisor advisor=new AuthorizationAttributeSourceAdvisor();
        advisor.setSecurityManager(securityManager);
        return advisor;
    }

}
