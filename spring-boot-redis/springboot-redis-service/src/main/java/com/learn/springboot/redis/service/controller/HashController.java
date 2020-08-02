package com.learn.springboot.redis.service.controller;

import cn.hutool.core.util.StrUtil;
import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.model.entity.SysConfig;
import com.learn.springboot.redis.service.service.HashService;
import com.learn.springboot.redis.service.util.ValidatorUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * autor:liman
 * createtime:2020/8/2
 * comment:hash的controller
 */
@RestController
@Slf4j
@RequestMapping("hash")
public class HashController {

    @Autowired
    private HashService hashService;

    /**
     * 增加配置
     * @param config
     * @param result
     * @return
     */
    @RequestMapping(value = "put",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse put(@RequestBody @Validated SysConfig config, BindingResult result){
        String checkRes= ValidatorUtil.checkResult(result);
        if (StrUtil.isNotBlank(checkRes)){
            return new BaseResponse(StatusCode.Fail.getCode(),checkRes);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            hashService.addSysConfigInfo(config);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 获取配置
     * @return
     */
    @RequestMapping(value = "get",method = RequestMethod.GET)
    public BaseResponse get(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(hashService.getAll());

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 按照类型获取配置信息
     * @param type
     * @return
     */
    @RequestMapping(value = "get/type",method = RequestMethod.GET)
    public BaseResponse getType(@RequestParam String type){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(hashService.getByType(type));

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
