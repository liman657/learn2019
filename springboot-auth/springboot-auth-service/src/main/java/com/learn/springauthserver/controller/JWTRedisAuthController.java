package com.learn.springauthserver.controller;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.service.JWTAuthService;
import com.learn.springauthserver.service.JWTRedisAuthService;
import com.learn.springauthserver.utils.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * autor:liman
 * createtime:2019/12/15
 * comment:单纯的JWT认证
 */
@RestController
@Slf4j
@RequestMapping("/JWTRedisAuth")
public class JWTRedisAuthController {

    @Autowired
    private JWTRedisAuthService jwtRedisAuthService;


    @RequestMapping(value="login",method=RequestMethod.POST)
    public BaseResponse login(@RequestParam String userName,@RequestParam String password){
        log.info("[JWT+Redis认证开始]，获取token,用户名:{}，密码:{}",userName,password);
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
            return new BaseResponse(StatusCode.UserNamePasswordNotBlank);
        }
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try {
            result.setData(jwtRedisAuthService.authAndCreateToken(userName,password));
        }catch (Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="token/unauth",method=RequestMethod.GET)
    public BaseResponse jwtRedisTokenUnauth(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            String info = "[JWT+Redis认证]不需要认证即可访问的资源";
            result.setData(info);
        }catch (Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="token/auth",method=RequestMethod.GET)
    public BaseResponse jwtRedisTokenAuth(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            String info = "[JWT+Redis认证]需要认证才能访问的资源";
            result.setData(info);
        }catch (Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     * 修改密码
     * @param accessToken
     * @param updatePsdDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="/password/update",method=RequestMethod.POST)
    public BaseResponse updatePassword(@RequestHeader String accessToken ,@RequestBody @Validated UpdatePsdDto updatePsdDto
            ,BindingResult bindingResult){
        log.info("[JWT+Redis认证]开始修改密码");
        if(StringUtils.isBlank(accessToken)){
            return new BaseResponse(StatusCode.AccessTokenNotBlank);
        }
        String res = ValidatorUtils.checkResult(bindingResult);
        if(StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            jwtRedisAuthService.updatePassword(accessToken,updatePsdDto);
        }catch (Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="logout",method=RequestMethod.GET)
    public BaseResponse logout(@RequestHeader String accessToken){
        if(StringUtils.isBlank(accessToken)){
            return new BaseResponse(StatusCode.CurrUserNotLogin);
        }
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            jwtRedisAuthService.invalidateByAccessToken(accessToken);
        }catch (Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

}
