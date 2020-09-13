package com.learn.springauthserver.controller;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.redis.RedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * autor:liman
 * createtime:2019/12/15
 * comment:
 */
@RestController
@Slf4j
@RequestMapping("/redis")
public class RedisController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value="/test",method = RequestMethod.GET)
    public BaseResponse testRedis(@RequestParam("redisValue") String redisValue){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{
            redisService.testRedis(redisValue);
            redisService.testStringTemplate(redisValue);
        }catch (Exception e){

        }
        return result;
    }

}
