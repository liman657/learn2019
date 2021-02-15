package com.liman.learn.pmp.shiro;

import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.stereotype.Component;

/**
 * autor:liman
 * createtime:2021/2/6
 * comment:
 */
@Component
@Slf4j
public class ShiroVariable {

    /**
     * 判断当前登录用户是否具有访问指定资源的权限
     * @param permission 资源标示
     * @return
     */
    public boolean hasPermission(String permission){

        Subject subject = SecurityUtils.getSubject();
        boolean result = false;
        if(null!=subject) {
            result = subject.isPermitted(permission);
        }
        log.info("{}，权限认证结果为:{}",permission,result);
        return result;
    }

}
