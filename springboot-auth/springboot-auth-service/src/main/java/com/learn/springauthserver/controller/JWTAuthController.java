package com.learn.springauthserver.controller;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.redis.RedisService;
import com.learn.springauthserver.service.JWTAuthService;
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
@RequestMapping("/JWTAuth")
public class JWTAuthController {

    @Autowired
    private JWTAuthService jwtAuthService;

    @RequestMapping(value="/login",method=RequestMethod.POST)
    public BaseResponse login(@RequestParam String userName,@RequestParam String password){
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
            return new BaseResponse(StatusCode.UserNamePasswordNotBlank);
        }
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            result.setData(jwtAuthService.createAuthModelToken(userName,password));
            log.info("[jwt认证]生成token成功,{}",result);
        }catch (Exception e ){
            log.error("[jwt认证]生成token失败,{}",e.fillInStackTrace());
            result = new BaseResponse(StatusCode.Fail);
        }
        return result;
    }

    @RequestMapping(value="token/auth",method = RequestMethod.GET)
    public BaseResponse jwtTokenAuth(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try {
            String info = "this is jwt auth resource";
            result.setData(info);
            log.info("[jwt认证]访问认证资源成功");
        }catch (Exception e){
            log.error("[jwt认证]访问认证资源失败");
            result = new BaseResponse(StatusCode.Fail);
        }
        return result;
    }

    @RequestMapping(value="token/unauth",method = RequestMethod.GET)
    public BaseResponse jwtTokenUnAuth(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try {
            String info = "this is jwt unauth resource";
            result.setData(info);
            log.info("[jwt认证]访问非认证资源成功");
        }catch (Exception e){
            log.error("[jwt认证]访问非认证资源失败");
            result = new BaseResponse(StatusCode.Fail);
        }
        return result;
    }

    @RequestMapping(value="token/password/update",method=RequestMethod.POST)
    public BaseResponse updatePassword(@RequestHeader String accessToken,@RequestBody @Validated UpdatePsdDto updatePsdDto,
                                       BindingResult bindingResult){
        log.info("[jwt认证]开始修改密码");
        if(StringUtils.isBlank(accessToken)){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        String res = ValidatorUtils.checkResult(bindingResult);
        if(StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }

        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            jwtAuthService.updatePassword(accessToken,updatePsdDto);
        }catch (Exception e){
            log.error("[jwt]认证失败，失败信息为:{}",e.fillInStackTrace());
            result = new BaseResponse(StatusCode.Fail);
        }
        return result;
    }

    /**
     * 这里的退出，需要前端清除JWT token，由于JWT后端不维护，因此不需要做任何操作
     * @param accessToken
     * @return
     */
    @RequestMapping(value="token/logout",method = RequestMethod.GET)
    public BaseResponse logout(@RequestHeader String accessToken){
        if(StringUtils.isBlank(accessToken)){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        return new BaseResponse(StatusCode.Success);
    }

}
