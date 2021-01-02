package com.liman.learn.pmp.util;

import com.liman.learn.common.exception.CommonException;
import com.liman.learn.pmp.model.entity.SysUserEntity;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;

/**
 * autor:liman
 * createtime:2021/1/2
 * comment: ShiroUtil工具类，包含Shiro加解密方式的封装
 */
@Slf4j
public class ShiroUtil {
    //加密算法
    public final static String hashAlgorithmName = "SHA-256";

    //循环次数
    public final static int hashIterations = 16;

    public static String sha256(String password, String salt) {
        return new SimpleHash(hashAlgorithmName, password, salt, hashIterations).toString();
    }

    //获取Shiro Session
    public static Session getSession() {
        return SecurityUtils.getSubject().getSession();
    }

    //获取Shiro Subject
    public static Subject getSubject() {
        return SecurityUtils.getSubject();
    }

    //获取Shiro中的真正主体
    public static SysUserEntity getUserEntity() {
        return (SysUserEntity)SecurityUtils.getSubject().getPrincipal();
    }

    public static Long getUserId() {
        return getUserEntity().getUserId();
    }

    public static void setSessionAttribute(Object key, Object value) {
        getSession().setAttribute(key, value);
    }

    public static Object getSessionAttribute(Object key) {
        return getSession().getAttribute(key);
    }

    public static boolean isLogin() {
        return SecurityUtils.getSubject().getPrincipal() != null;
    }

    public static void logout() {
        SecurityUtils.getSubject().logout();
    }

    /**
     * 获取验证码
     * @param key KAPTCHA_SESSION_KEY
     * @return
     */
    public static String getKaptcha(String key) {
        Object object=getSessionAttribute(key);
        if (object==null){
            throw new CommonException("验证码已失效!");
        }
        String newCode=object.toString();
        getSession().removeAttribute(key);
        log.info("新的验证码：{}",newCode);

        return newCode;
    }
}
