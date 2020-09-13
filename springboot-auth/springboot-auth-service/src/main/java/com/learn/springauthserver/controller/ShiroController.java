package com.learn.springauthserver.controller;

import com.alibaba.druid.sql.builder.UpdateBuilder;
import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.service.ShiroService;
import com.learn.springauthserver.utils.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


/**
 * autor:liman
 * createtime:2019/12/23
 * comment:
 */
@RestController
@Slf4j
@RequestMapping("shiro")
public class ShiroController {

    @Autowired
    private ShiroService shiroService;

    /**
     * 登录
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value="login",method = RequestMethod.POST)
    public BaseResponse login(@RequestParam String userName, @RequestParam String password){
        if(StringUtils.isBlank(userName) || StringUtils.isBlank(password)){
            return new BaseResponse(StatusCode.UserNamePasswordNotBlank);
        }

        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            //登录认证过程交给了shiro完成，
            Subject subject = SecurityUtils.getSubject();

            if(!subject.isAuthenticated()){//如果没有认证过，调用shiro认证
                UsernamePasswordToken token = new UsernamePasswordToken(userName,password);
                subject.login(token);
            }

            if(subject.isAuthenticated()){//如果认证过，直接获取subject中的主体数据返回
                result.setData(SecurityUtils.getSubject().getPrincipal());
            }
        }catch(Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     * 访问需要认证的资源
     * @return
     */
    @RequestMapping(value="auth",method=RequestMethod.GET)
    public BaseResponse tokenAuth(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap = new HashMap<>();
        try{
            String info = "shiro~~ 需认证才可访问的资源";
            resMap.put("info",info);
            resMap.put("currUser",SecurityUtils.getSubject().getPrincipal());
            result.setData(resMap);
        }catch (Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     * 访问无需认证的资源
     * @return
     */
    @RequestMapping(value="unauth",method=RequestMethod.GET)
    public BaseResponse tokenUnAuth(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            String info = "shiro~~，无需认证即可访问的资源";
            result.setData(info);
        }catch (Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     * 退出
     * @return
     */
    @RequestMapping(value="logout",method=RequestMethod.GET)
    public BaseResponse logout(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            SecurityUtils.getSubject().logout();//取消shiro中subject的认证
        }catch (Exception e){
            result = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return result;
    }

    /**
     * 修改密码
     * @param dto
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="updatepassword",method = RequestMethod.POST)
    public BaseResponse updatePassword(@RequestBody @Validated UpdatePsdDto dto, BindingResult bindingResult){
        log.info("--shiro~修改密码--");

        String res= ValidatorUtils.checkResult(bindingResult);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            shiroService.updatePassword(dto);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}