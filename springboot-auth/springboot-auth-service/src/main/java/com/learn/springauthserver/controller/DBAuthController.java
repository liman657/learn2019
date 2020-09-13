package com.learn.springauthserver.controller;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthmodel.entity.User;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.service.DBAuthService;
import com.learn.springauthserver.utils.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * autor:liman
 * createtime:2019/12/11
 * comment:token+数据库 controller
 */
@RestController
@Slf4j
@RequestMapping("/dbAuth")
public class DBAuthController {

    @Autowired
    private DBAuthService dbAuthService;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResponse login(@RequestParam String userName, @RequestParam String password) {
        if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
            return new BaseResponse(StatusCode.UserNamePasswordNotBlank);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {//如果登录成功，则创建token
            response.setData(dbAuthService.authAndCreateToken(userName, password));
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/token/auth", method = RequestMethod.GET)
    public BaseResponse tokenAuth() {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            String data = "这里是需要认证才能访问的资源";
            baseResponse.setData(data);
        } catch (Exception e) {
            baseResponse = new BaseResponse(StatusCode.Fail, e.getMessage());
        }
        return baseResponse;
    }


    @RequestMapping(value = "token/unauth", method = RequestMethod.GET)
    public BaseResponse tokenUnAuth() {
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            String data = "这里是不需要认证就能访问的资源";
            baseResponse.setData(data);
        } catch (Exception e) {
            baseResponse = new BaseResponse(StatusCode.Fail, e.getMessage());
        }
        return baseResponse;
    }

    /**
     * 修改密码的时候，需要失效token
     *
     * @param accessToken
     * @param updatePsdDto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/token/password/update", method = RequestMethod.POST)
    public BaseResponse updatePassword(@RequestHeader String accessToken, @RequestBody @Validated UpdatePsdDto updatePsdDto
            , BindingResult bindingResult) {
        log.info("客户开始修改密码，需要失效之前的token");
        BaseResponse baseResponse = new BaseResponse(StatusCode.Success);
        try {
            if (StringUtils.isBlank(accessToken)) {
                return new BaseResponse(StatusCode.AccessTokenNotBlank);
            }
            //参数校验，加了@Validated注解的参数，校验的结果都会放到bindingResult中。
            String res = ValidatorUtils.checkResult(bindingResult);
            if (StringUtils.isNotBlank(res)) {
                return new BaseResponse(StatusCode.InvalidParams.getCode(), res);
            }
            //开始修改密码
            dbAuthService.updatePassword(accessToken,updatePsdDto);

        } catch (Exception e) {
            baseResponse = new BaseResponse(StatusCode.Fail, e.getMessage());
        }
        return baseResponse;
    }

    @RequestMapping(value="token/logout",method =RequestMethod.GET)
    public BaseResponse logout(@RequestHeader String accessToken){
        BaseResponse result= new BaseResponse(StatusCode.Success);
        if(StringUtils.isBlank(accessToken)){
            return new BaseResponse(StatusCode.AccessTokenNotBlank);
        }
        try{
            //这里需要失效token
            dbAuthService.invalidateAccessToken(accessToken);
        }catch (Exception e){
            log.error("退出异常，异常信息为:{}",e.fillInStackTrace());
            return new BaseResponse(StatusCode.Fail);
        }
        return result;
    }

    /**
     * 管理员增加用户信息
     * @return
     */
    @RequestMapping(value="token/saveUser",method=RequestMethod.POST)
    public BaseResponse saveUser(@RequestHeader String accessToken, @RequestBody @Validated User user
            ,BindingResult bindingResult){
        if (StringUtils.isBlank(accessToken)){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse result= new BaseResponse(StatusCode.Success);
        try{
            String valiRes = ValidatorUtils.checkResult(bindingResult);
            if(StringUtils.isNotBlank(valiRes)){
                return new BaseResponse(StatusCode.InvalidParams.getCode(),valiRes);
            }

            dbAuthService.saveUser(accessToken,user);
        }catch (Exception e){
            log.error("新增用户异常,异常信息为:{}",e.fillInStackTrace());
            return new BaseResponse(StatusCode.Fail);
        }
        return result;
    }
}
