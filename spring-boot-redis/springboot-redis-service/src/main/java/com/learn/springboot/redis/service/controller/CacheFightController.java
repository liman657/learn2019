package com.learn.springboot.redis.service.controller;

import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.service.service.CacheFightService;
import com.learn.springboot.redis.service.service.redis.StringRedisService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * autor:liman
 * createtime:2020/8/6
 * comment:cacheFightController，缓存穿透的实例
 */
@Slf4j
@RestController
@RequestMapping("cache/fight")
public class CacheFightController {

    @Autowired
    private CacheFightService cacheFightService;

    /**
     * 缓存穿透
     * @param id
     * @return
     */
    @RequestMapping(value="through",method = RequestMethod.GET)
    public BaseResponse get(@RequestParam Integer id){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(cacheFightService.getItem(id));
        }catch (Exception e){
            log.error("--典型应用场景实战-模拟缓存穿透-发生异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail);
        }
        return response;
    }

    /**
     * 缓存穿透解决方案一
     * @param id
     * @return
     */
    @RequestMapping(value="through/solution1",method=RequestMethod.GET)
    public BaseResponse getSolutionOne(@RequestParam Integer id){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(cacheFightService.getItemSolutionOne(id));
        }catch (Exception e){
            log.error("--典型应用场景实战-模拟缓存穿透-发生异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail);
        }
        return response;
    }

    /**
     * 缓存穿透解决方案二
     * @param id
     * @return
     */
    @RequestMapping(value="through/solution2",method=RequestMethod.GET)
    public BaseResponse getSolutionTwo(@RequestParam Integer id){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(cacheFightService.getItemSolutioinTwo(id));
        }catch (Exception e){
            log.error("--典型应用场景实战-模拟缓存穿透-发生异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail);
        }
        return response;
    }
}
