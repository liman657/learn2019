package com.learn.springauthserver.redis;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthmodel.entity.AuthTokenModel;
import com.learn.springauthmodel.entity.User;
import com.learn.springauthserver.contants.Constant;
import com.learn.springauthserver.dto.AccessTokenDto;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.service.UserService;
import com.learn.springauthserver.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.tomcat.util.bcel.Const;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.concurrent.TimeUnit;

/**
 * autor:liman
 * createtime:2019/12/15
 * comment:
 */
@Service
@Slf4j
public class RedisService {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private RedisTemplate redisTemplate;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    /**
     * 用于测试
     * @param redisInfo
     */
    public void testRedis(String redisInfo){
        log.info("开始存入缓存");
        redisTemplate.opsForValue().set("test",redisInfo);
        Object testObject = redisTemplate.opsForValue().get("test");
        log.info("从缓存中获取的信息:{}",testObject);
    }

    /**
     * 用于测试
     * @param redisInfo
     */
    public void testStringTemplate(String redisInfo){
        log.info("开始测试StringTemplate");
        stringRedisTemplate.opsForValue().set("testString","testStringRedis");
        String testRedisString = stringRedisTemplate.opsForValue().get("testString");
        log.info("从缓存中获得的String类型:{}",testRedisString);
    }

    /**
     * 认证并产生token
     * @param userName
     * @param password
     * @return
     */
    public AuthTokenModel authAndCreateToken(String userName, String password) throws Exception {
        //用户名密码的认证依旧来自数据库的数据
        User user = userService.authUser(userName, password);
        if(user!=null){
            Long timestamp = System.currentTimeMillis();

            AccessTokenDto accessTokenDto = new AccessTokenDto(user.getId(),userName,
                    timestamp, Constant.snowFlake.nextId().toString(),Constant.ACCESS_TOKEN_EXPIRE);
            String jsonStr = objectMapper.writeValueAsString(accessTokenDto);
            log.info("--redis认证生成的token的json字符串--:{}",jsonStr);
            //根据客户关键信息生成token
            String accessToken = EncryptUtil.aesEncrypt(jsonStr,Constant.TOKEN_AUTH_KEY);

            //开始缓存生成的token
            stringRedisTemplate.opsForValue().set(Constant.TOKEN_REDIS_KEY_PREFIX+userName,
                    accessToken,Constant.ACCESS_TOKEN_EXPIRE,TimeUnit.MILLISECONDS);
            
            log.info("--token生成成功，已经存入缓存--");
            AuthTokenModel authTokenModel = new AuthTokenModel(accessToken, Constant.ACCESS_TOKEN_EXPIRE);
            return authTokenModel;
        }
        return null;
    }

    /**
     * redis的验证token
     * @param accessToken
     * @return
     */
    public BaseResponse validateToken(final String accessToken){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            if(StringUtils.isBlank(accessToken)){
                return new BaseResponse(StatusCode.AccessTokenNotBlank);
            }

            //解析token
            AccessTokenDto accessTokenDto = decodeAccessToken(accessToken);
            if(StringUtils.isBlank(accessTokenDto.getUserName())){
                return new BaseResponse(StatusCode.AccessTokenInvalidate);
            }

            //查看redis中token是否存在
            final String key = Constant.TOKEN_REDIS_KEY_PREFIX+accessTokenDto.getUserName();
            Boolean isExist = stringRedisTemplate.hasKey(key);//这个redis的方式，如果存在这个可以，则直接返回true
            if(!isExist){//token已经不存在，失效
                return new BaseResponse(StatusCode.AccessTokenNotExist);
            }

            //开始对比token，这个操作在数据库认证中没有
            String redisToken = stringRedisTemplate.opsForValue().get(key);
            if(!accessToken.equals(redisToken)){
                return new BaseResponse(StatusCode.AccessTokenInvalidate);
            }

        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }


    /**
     * 解析token
     * @param accessToken
     * @return
     */
    public AccessTokenDto decodeAccessToken(String accessToken) throws Exception {
        String jsonStr = EncryptUtil.aesDecrypt(accessToken,Constant.TOKEN_AUTH_KEY);
        return objectMapper.readValue(jsonStr,AccessTokenDto.class);
    }

    @Transactional(rollbackFor = Exception.class)
    public void updatePassword(String accessToken, UpdatePsdDto updatePsdDto) throws Exception {
        if(StringUtils.isNotBlank(accessToken)){
            //解析完成token之后可以获取用户信息
            AccessTokenDto accessTokenDto = decodeAccessToken(accessToken);
            //开始修改密码
            User user = userService.selectUserByUserName(accessTokenDto.getUserName());
            if(user==null){
                throw new RuntimeException("当前token对应的用户无效");
            }
            log.info("{},开始修改密码,原密码为:{},新密码为:{}",
                    accessTokenDto.getUserName(),updatePsdDto.getOldPassword(),updatePsdDto.getNewPassword());
            if(!user.getPassword().equals(updatePsdDto.getOldPassword())){
                throw new RuntimeException("原密码不匹配");
            }



            //修改密码
            int res = userService.updatePassword(accessTokenDto,updatePsdDto);
            if(res<=0){
                throw new RuntimeException("修改密码失败,请重新尝试");
            }

            //失效原有的token
            invalidateByAccessToken(accessToken);

        }
    }

    /**
     * 失效指定的token，只是简单的从缓存中删除该token
     * @param accessToken
     * @throws Exception
     */
    public void invalidateByAccessToken(final String accessToken) throws Exception {
        if(StringUtils.isNotBlank(accessToken)){
            AccessTokenDto accessTokenDto = decodeAccessToken(accessToken);
            if(StringUtils.isNotBlank(accessTokenDto.getUserName())){
                final String key = Constant.TOKEN_REDIS_KEY_PREFIX+accessTokenDto.getUserName();
                if(stringRedisTemplate.hasKey(key)){
                    stringRedisTemplate.delete(key);
                }
            }
        }
    }
}
