package com.learn.springauthserver.service;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthmodel.entity.AuthTokenModel;
import com.learn.springauthmodel.entity.User;
import com.learn.springauthserver.contants.Constant;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.utils.JWTRedisUtils;
import com.learn.springauthserver.utils.JWTUtils;
import io.jsonwebtoken.Claims;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2019/12/18
 * comment:JWT认证service
 */
@Service
@Slf4j
public class JWTRedisAuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Transactional(rollbackFor = Exception.class)
    public AuthTokenModel authAndCreateToken(String userName,String password){
        log.info("[JWT+Redis认证]开始认证并获取token,用户名:{},密码:{}",userName,password);
        User user = userService.authUser(userName,password);
        if(user!=null){
            String accessToken = JWTRedisUtils.createJWT(user.getId().toString(),user.getUserName());

            //将token放入redis
            stringRedisTemplate.opsForValue().set(Constant.TOKEN_REDIS_KEY_PREFIX+user.getId(),accessToken,
                    Constant.ACCESS_TOKEN_EXPIRE,TimeUnit.MILLISECONDS);
            AuthTokenModel authTokenModel = new AuthTokenModel(accessToken,Constant.ACCESS_TOKEN_EXPIRE);
            log.info("[JWT+Redis认证]创建token成功:{}",authTokenModel);
            return authTokenModel;
        }
        return null;
    }

    /**
     * jwt解析token
     * @param accessToken
     * @return
     */
    public BaseResponse validateToken(final String accessToken){
        log.info("开始解析token:{}",accessToken);
        try{
            Claims claims = JWTRedisUtils.validateJwtToken(accessToken);
            if(claims==null || StringUtils.isBlank(claims.getId())){
                return new BaseResponse(StatusCode.AccessTokenInvalidate);
            }

            //解析完了之后，就可以拿到key了
            String key = Constant.JWT_TOKEN_REDIS_KEY_PREFIX+claims.getId();
            //如果key不存在，则表示token过期
            if(!stringRedisTemplate.hasKey(key)){
                return new BaseResponse(StatusCode.AccessTokenNotExistRedis);
            }

            //解析一下token，并判断是否相等
            String token = stringRedisTemplate.opsForValue().get(key);
            if(!accessToken.equals(token)){
                return new BaseResponse(StatusCode.AccessTokenInvalidate);
            }

            //校验token成功
            return new BaseResponse(StatusCode.Success);
        }catch (Exception e){
            log.error("[JWT+Redis认证]解析token异常，异常信息为:{}",e.fillInStackTrace());
            return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
    }

    /**
     * 修改密码
     * @param accessToken
     * @param updatePsdDto
     */
    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(final String accessToken ,final UpdatePsdDto updatePsdDto){
        if(StringUtils.isNotBlank(accessToken)){
            //解析token，获取用户信息
            Claims claims = JWTRedisUtils.validateJwtToken(accessToken);

            User user = userService.selectUserByUserName(claims.getSubject());
            if(user==null){
                throw new RuntimeException("当前token对应的用户是无效的");
            }

            if(!user.getPassword().equals(updatePsdDto.getOldPassword())){
                throw new RuntimeException("旧密码不匹配!");
            }

            //修改密码
            int res = userService.updatePassword(claims.getSubject(),updatePsdDto.getOldPassword(),updatePsdDto.getNewPassword());
            if(res<=0){
                throw new RuntimeException("修改密码失败，请重新尝试");
            }

            //失效以前的token
            invalidateByAccessToken(accessToken);
        }
    }

    /**
     * 失效指定的accessToken
     * @param accessToken
     * redis 中删除指定的项
     */
    public void invalidateByAccessToken(final String accessToken){
        if(StringUtils.isNotBlank(accessToken)){
            Claims claims = JWTRedisUtils.validateJwtToken(accessToken);

            final String key = Constant.JWT_TOKEN_REDIS_KEY_PREFIX+claims.getId();
            if(stringRedisTemplate.hasKey(key)){
                stringRedisTemplate.delete(key);
            }
        }
    }

}
