package com.learn.springboot.redis.service.controller;

import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.bind.annotation.*;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:baseContoller
 */
@Slf4j
@RestController
public class BaseController {

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    private static final String RedisHelloWorldKey = "SpringbootRedis:HelloWorld";

    @RequestMapping(value="info",method=RequestMethod.POST)
    @ResponseBody
    public String info(){
        String name = "redis在springboot中的实例";
        return name;
    }

    @RequestMapping(value = "/helloworld/put",method = RequestMethod.POST)
    @ResponseBody
    public BaseResponse helloWorldPut(@RequestBody String helloName){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            stringRedisTemplate.opsForValue().set(RedisHelloWorldKey,helloName);
            response.setData("Redis存入数据成功");
        }catch (Exception e){
            log.info("--hello world put异常信息--： ",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
