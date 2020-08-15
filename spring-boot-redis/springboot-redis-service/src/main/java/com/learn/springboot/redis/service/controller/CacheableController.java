package com.learn.springboot.redis.service.controller;

import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.service.service.cacheable.CacheableService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * autor:liman
 * createtime:2020/8/15
 * comment:这里主要是@Cacheable的实例
 */
@Slf4j
@RestController
@RequestMapping("/cacheable")
public class CacheableController {

    @Autowired
    private CacheableService cacheableService;

    //获取详情-@Cacheable
    @RequestMapping(value = "info",method = RequestMethod.GET)
    public BaseResponse info(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(cacheableService.getInfo(id));

        }catch (Exception e){
            log.error("--商品详情controller-详情-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //获取详情
    @RequestMapping(value = "info/v2",method = RequestMethod.GET)
    public BaseResponse infoV2(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(cacheableService.getInfoV2(id));

        }catch (Exception e){
            log.error("--商品详情controller-详情-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //删除
    @RequestMapping(value = "delete",method = RequestMethod.GET)
    public BaseResponse delete(@RequestParam Integer id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            cacheableService.delete(id);
        }catch (Exception e){
            log.error("--商品详情controller-删除-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
