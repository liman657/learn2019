package com.learn.springboot.redis.service.controller;

import cn.hutool.core.util.StrUtil;
import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.model.entity.User;
import com.learn.springboot.redis.service.service.SetService;
import com.learn.springboot.redis.service.service.problem.ProblemService;
import com.learn.springboot.redis.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.validation.BindingResult;

/**
 * autor:liman
 * createtime:2020/7/29
 * comment:
 */
@RestController
@Slf4j
@RequestMapping("set")
public class SetController {

    @Autowired
    private SetService setService;

    @Autowired
    private ProblemService problemService;

    @RequestMapping(value="put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse put(@RequestBody @Validated User user, BindingResult bindResult){
        String checkRes=ValidatorUtil.checkResult(bindResult);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            log.info("-----用户注册信息:{}-----",user);
            response.setData(setService.registerUser(user));
        }catch (Exception e){
            log.error("用户注册出现异常，异常信息为：{}",e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value="get",method=RequestMethod.GET)
    public BaseResponse get(@RequestParam("userId") int userId){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(setService.getEmails(userId));
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "problems/random",method = RequestMethod.GET)
    public BaseResponse getRandomProblems(@RequestParam Integer total){
        if (total<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(problemService.getRandomEntitys(total));

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
