package com.learn.springboot.redis.service.controller;

import cn.hutool.core.collection.CollUtil;
import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.model.entity.PhoneFare;
import com.learn.springboot.redis.service.service.ZSetService;
import com.learn.springboot.redis.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.ibatis.annotations.Param;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:
 */
@RestController
@Slf4j
@RequestMapping("/zset")
public class ZSetController {

    @Autowired
    private ZSetService zSetService;

    @RequestMapping(value = "put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse put(@RequestBody @Validated PhoneFare fare, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(zSetService.addRecord(fare));

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "get",method = RequestMethod.GET)
    public BaseResponse get(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();
        try {
            resMap.put("SortFaresASC",zSetService.getSortFares(true));
            resMap.put("SortFaresDESC",zSetService.getSortFares(false));

        }catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        response.setData(resMap);
        return response;
    }

    @RequestMapping(value = "putUpdate",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse putUpdate(@RequestBody @Validated PhoneFare fare, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
           response.setData(zSetService.addRecordUpdate(fare));

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "getUpdate",method = RequestMethod.GET)
    public BaseResponse getUpdate(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();
        try {
            resMap.put("sortList",zSetService.getSortFaresUpdate());
//            resMap.put("SortFaresDESC",zSetService.getSortFaresUpdate(false));

        }catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        response.setData(resMap);
        return response;
    }

}
