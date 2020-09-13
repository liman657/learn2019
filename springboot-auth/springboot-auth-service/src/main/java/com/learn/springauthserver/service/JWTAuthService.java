package com.learn.springauthserver.service;

import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthmodel.entity.AuthTokenModel;
import com.learn.springauthmodel.entity.User;
import com.learn.springauthserver.contants.Constant;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import jdk.internal.dynalink.support.RuntimeContextLinkRequestImpl;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2019/12/18
 * comment:JWT认证service
 */
@Service
@Slf4j
public class JWTAuthService {

    @Autowired
    private UserService userService;

    /**
     * 数据库认证用户名和密码，然后生成jwt的token
     * @param userName
     * @param password
     * @return
     */
    public AuthTokenModel createAuthModelToken(String userName,String password){
        User user = userService.authUser(userName, password);
        if(user!=null){
            String jwtToken = JWTUtils.createJWT(user.getId().toString(),userName, Constant.ACCESS_TOKEN_EXPIRE);
            log.info("[jwt认证]用户登录成功，JWTtoken生成成功:{}",jwtToken);

            AuthTokenModel authTokenModel = new AuthTokenModel(jwtToken,Constant.ACCESS_TOKEN_EXPIRE);
            return authTokenModel;
        }
        return null;
    }

    /**
     * Jwt验证解析token
     * @param jwtToken
     * @return
     */
    public BaseResponse validateJwtToken(final String jwtToken){
        return JWTUtils.validateJwtToken(jwtToken);
    }

    /**
     * 修改密码，由于JWT是完全交给了前端控制，因此这里不需要让其失效，直接修改密码即可。
     * @param jwtToken
     * @param updatePsdDto
     */
    public void updatePassword(final String jwtToken, final UpdatePsdDto updatePsdDto){
        if(StringUtils.isBlank(jwtToken)){
            Claims claims = JWTUtils.parseJWT(jwtToken);
            if(claims==null){
                throw new RuntimeException("当前token无效");
            }

            //在生成JWT的时候，就是以username作为主体的。
            User user = userService.selectUserByUserName(claims.getSubject());
            if(user==null){
                throw new RuntimeException("当前token对应的无效的用户");
            }

            //验证旧密码是否相等
            if(!user.getPassword().equals(updatePsdDto.getOldPassword())){
                throw new RuntimeException("旧密码不匹配");
            }

            int res = userService.updatePassword(claims.getSubject(),updatePsdDto.getOldPassword(),updatePsdDto.getNewPassword());
        }
    }

}
