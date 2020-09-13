package com.learn.springauthserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthmodel.entity.AuthToken;
import com.learn.springauthmodel.entity.AuthTokenModel;
import com.learn.springauthmodel.entity.Log;
import com.learn.springauthmodel.entity.User;
import com.learn.springauthmodel.mapper.AuthTokenMapper;
import com.learn.springauthserver.contants.Constant;
import com.learn.springauthserver.dto.AccessTokenDto;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.utils.EncryptUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.joda.time.DateTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.awt.image.BufferStrategy;
import java.util.Date;

/**
 * autor:liman
 * createtime:2019/12/11
 * comment:token+数据库认证服务类
 */
@Service
@Slf4j
public class DBAuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private LogService logService;

    @Autowired
    private AuthTokenMapper authTokenMapper;

    @Transactional(rollbackFor = Exception.class)//加入事务控制
    public AuthTokenModel authAndCreateToken(String userName, String password) throws Exception {
        User user = userService.authUser(userName,password);
        if(user!=null){
            //正式创建token之前需要实现之前的token
            authTokenMapper.invalidateTokenByUser(user.getId());

            //创建token
            //1.生成时间戳和随机串
            Long timeStamp = System.currentTimeMillis();
            String randomStr = Constant.snowFlake.nextId().toString();//利用雪花算法，产生随机串

            //2.利用dto构建生成token需要的元素
            AccessTokenDto tokenDto = new AccessTokenDto(user.getId(),user.getUserName()
                    ,timeStamp,randomStr
                    ,Constant.ACCESS_TOKEN_EXPIRE);
            String tokenJsonStr = objectMapper.writeValueAsString(tokenDto);

            //3.生成真正的token
            String token = EncryptUtil.aesEncrypt(tokenJsonStr, Constant.TOKEN_AUTH_KEY);   //AES加密算法生成的token

            //4.将token返回给前端
            AuthTokenModel authToken = new AuthTokenModel(token,Constant.ACCESS_TOKEN_EXPIRE);

            //5.保存token到数据库
            AuthToken authTokenEntity = new AuthToken();
            authTokenEntity.setUserId(user.getId());
            authTokenEntity.setAccessToken(token);
            authTokenEntity.setAccessExpire(Constant.ACCESS_TOKEN_EXPIRE);
            authTokenEntity.setTokenTimestamp(timeStamp);
            authTokenEntity.setCreateTime(DateTime.now().toDate());
            authTokenMapper.insertSelective(authTokenEntity);
            log.info("[token]+数据库用户认证成功，成功生成accessToken--");
            return authToken;
        }
        return null;
    }

    /**
     * 验证并解析token
     * @param accessToken
     * @return
     */
    public BaseResponse validateToken(final String accessToken){
        log.info("开始验证token:{}",accessToken);
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            if(StringUtils.isBlank(accessToken)){
                return new BaseResponse(StatusCode.AccessTokenNotBlank);
            }

            //验证token是否在数据库中是否存在
            AuthToken authToken = authTokenMapper.selectByAccessToken(accessToken);
            if(authToken==null){
                log.info("token 不存在:{}",accessToken);
                return new BaseResponse(StatusCode.AccessTokenNotExist);
            }

            //解析token，为了防止伪造，需要解析一下，看是否合法
            AccessTokenDto accessTokenDto;
            try{
                log.info("开始解析token:{}",accessToken);
                accessTokenDto = decodeAccessToken(accessToken);
            }catch (Exception e){//解析异常，token不合法
                return new BaseResponse(StatusCode.AccessTokenInvalidate);
            }

            //验证token是否过期
            if(accessTokenDto != null){
                log.info("开始验证token是否过期:{}",accessToken);
                if(System.currentTimeMillis()-accessTokenDto.getTimestamp() > accessTokenDto.getExpire()){
                    invalidateAccessToken(accessToken);
                    return new BaseResponse(StatusCode.TokenValidateExpireToken);
                }
            }


        }catch (Exception e){
            return new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        log.info("token验证通过:{}",accessToken);
        return response;
    }

    /**
     * 失效指定的token
     * @param accessToken
     */
    public void invalidateAccessToken(final String accessToken){
        if(StringUtils.isNotBlank(accessToken)){
            authTokenMapper.invalidateByToken(accessToken);
        }
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

    @Transactional
    public void updatePassword(final String accessToken, final UpdatePsdDto pwdDto) throws Exception {
        if(StringUtils.isNotBlank(accessToken)){
            //解析accesstoken
            AccessTokenDto tokenDto= decodeAccessToken(accessToken);
            //修改密码
            int res = userService.updatePassword(tokenDto,pwdDto);
            if(res<=0){
                log.error("密码修改失败");
            }
            //失效以前的token
            authTokenMapper.invalidateTokenByUser(tokenDto.getUserId());
        }
    }

    /**
     * 管理员增加用户的操作
     * @param accessToken
     * @param user
     */
    public void saveUser(String accessToken, User user) throws Exception {
        //1.验证token的合法性
        AccessTokenDto accessTokenDto = decodeAccessToken(accessToken);

        //2.保存用户
        userService.addUser(user);

        //3.记录操作日志
        Log logEntity = new Log(null,accessTokenDto.getUserId(),accessTokenDto.getUserName(),new Date(),"管理员增加一个普通用户");
        logService.addLog(logEntity);
    }
}