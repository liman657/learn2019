package com.liman.learn.pmp.shiro;

import com.liman.learn.pmp.model.entity.SysUserEntity;
import com.liman.learn.pmp.model.mapper.SysUserDao;
import com.liman.learn.pmp.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * autor:liman
 * createtime:2021/1/2
 * comment: 用于认证用户~授权
 * 需要继承至 AuthorizingRealm
 * 这个UserRalm 会交给securityManager去管理（在ShiroConig类中注入进去）
 */
@Component
@Slf4j
public class UserRealm extends AuthorizingRealm {

    @Autowired
    private SysUserDao sysUserDao;

    /**
     * 这个是资源的授权
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }

    /**
     * 这个才是认证，校验登录密码，等操作
     *
     * @param token
     * @return 需要返回一个 SimpleAuthenticationInfo
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        //token其实就是在controller中生成的UsernamePasswordToken
        UsernamePasswordToken usernamePasswordToken = (UsernamePasswordToken) token;
        String username = usernamePasswordToken.getUsername();
        String password = String.valueOf(usernamePasswordToken.getPassword());
        log.info("开始进行登录校验,参数——用户名:{},密码:{}", username, password);

        //这里开始进行登录校验，查数据库匹配用户名和密码
        SysUserEntity sysUserEntity = sysUserDao.selectByUserName(username);
        //账户不存在
        if (sysUserEntity == null) {
            //UnknownAccountException 这个异常是shiro提供的
            throw new UnknownAccountException("账户不存在!");
        }

        //账户被禁用
        if (0 == sysUserEntity.getStatus()) {
            //DisabledAccountException 这个异常是shiro提供的
            throw new DisabledAccountException("账户已被禁用,请联系管理员!");
        }
        /**
         * 开始密码校验，密码校验不同于我们传统的手动校验，shiro也提供了相关校验，可以直接利用shiro的校验
         */
//        if (!sysUserEntity.getPassword().equals(password)){
//            throw new IncorrectCredentialsException("账户密码不匹配!");
//        }
//        //这里没有使用shiro提供的密码匹配器
//        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(sysUserEntity,password,username);

        /**
         * 自己手动进行密码的匹配
         */
//        String realPassword=ShiroUtil.sha256(password,sysUserEntity.getSalt());
//        if (StringUtils.isBlank(realPassword) || !realPassword.equals(sysUserEntity.getPassword())){
//            throw new IncorrectCredentialsException("账户密码不匹配!");
//        }
//        SimpleAuthenticationInfo info=new SimpleAuthenticationInfo(sysUserEntity,password,getName());

        /**
         * 使用shiro提供的密码校验方式
         */
        SimpleAuthenticationInfo info = new SimpleAuthenticationInfo(sysUserEntity,
                sysUserEntity.getPassword(),
                ByteSource.Util.bytes(sysUserEntity.getSalt()),
                sysUserEntity.getName());

        return info;
    }

    /**
     * 复写父类的密码匹配器，用自己的密码匹配方式
     *
     * @param credentialsMatcher
     */
    @Override
    public void setCredentialsMatcher(CredentialsMatcher credentialsMatcher) {
        //使用hash的密码匹配器
        HashedCredentialsMatcher shaCredentialsMatcher = new HashedCredentialsMatcher();
        shaCredentialsMatcher.setHashAlgorithmName(ShiroUtil.hashAlgorithmName);//加密方式
        shaCredentialsMatcher.setHashIterations(ShiroUtil.hashIterations);//加密次数
        super.setCredentialsMatcher(shaCredentialsMatcher);
    }

    public static void main(String[] args) {
        String password = "root";
        String salt = "YzcmCZNvbXocrsz9dm8e";
        System.out.println(ShiroUtil.sha256(password, salt));
    }
}
