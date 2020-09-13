package com.learn.springauthserver.controller;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.service.SessionAuthService;
import com.learn.springauthserver.utils.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;

/**
 * autor:liman
 * createtime:2019/12/22
 * comment:
 */
@RestController
@Slf4j
@RequestMapping("/session")
public class SpringSessoinController {

    @Autowired
    private SessionAuthService sessionAuthService;

    @RequestMapping(value="login",method=RequestMethod.POST)
    public BaseResponse login(@RequestParam String userName, @RequestParam String password,HttpSession session){
        if(StringUtils.isBlank(userName)||StringUtils.isBlank(password)){
            return new BaseResponse(StatusCode.UserNamePasswordNotBlank);
        }
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            result.setData(sessionAuthService.authUser(userName,password,session));//TODO:这里的认证交给了service
            log.info("客户端保存的sessionId为:{}",session.getId());
        }catch (Exception e){
            result= new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="auth",method=RequestMethod.GET)
    public BaseResponse tokenAuth(HttpSession session){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            String info = "this is session auth resource";
            result.setData(info);
        }catch (Exception e){
            log.error("认证失败");
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="unauth",method = RequestMethod.GET)
    public BaseResponse tokenUnAuth(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            String info = "this is session [not] auth resource";
            result.setData(info);
        }catch (Exception e){
            log.error("访问资源失败");
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    @RequestMapping(value="password/update",method=RequestMethod.POST)
    public BaseResponse updatePassword(@RequestBody @Validated UpdatePsdDto updatePsdDto,
                                       BindingResult bindingResult,HttpSession session){
        log.info("===开始修改密码===");
        String res = ValidatorUtils.checkResult(bindingResult);
        if(StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            //TODO:service 修改密码
            sessionAuthService.updatePassword(updatePsdDto, session);
        }catch (Exception e){
            log.error("修改密码异常:{}",e.fillInStackTrace());
        }
        return result;
    }

    @RequestMapping(value="logout",method = RequestMethod.GET)
    public BaseResponse logout(HttpSession session){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            //TODO：这里需要让session失效
            sessionAuthService.invalidateSession(session);
        }catch (Exception e){
            log.error("退出系统异常,异常信息为:{}",e.fillInStackTrace());
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

}
