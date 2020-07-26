package com.learn.springboot.redis.service.controller;

import com.learn.springboot.redis.api.response.BaseResponse;
import com.learn.springboot.redis.api.response.StatusCode;
import com.learn.springboot.redis.model.entity.Item;
import com.learn.springboot.redis.service.service.StringService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;

/**
 * autor:liman
 * createtime:2020/7/26
 * comment:
 */
@RestController
@Slf4j
@RequestMapping("string")
public class StringController {

    @Autowired
    private StringService stringService;

    /**
     * 添加商品
     * @param item
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="put",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse put(@RequestBody @Validated Item item,BindingResult bindingResult){
        if(bindingResult.hasErrors()){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            log.info("--添加商品信息：{}--",item);
            stringService.addItem(item);
        }catch (Exception e){
            log.error("--字符串String实战-商品详情存储-添加-发生异常--：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "get",method = RequestMethod.GET)
    public BaseResponse get(@RequestParam Integer prodId){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            response.setData(stringService.getItem(prodId));

        }catch (Exception e){
            log.error("--Redis字符串String实战-商品详情存储-获取-发生异常：",e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
