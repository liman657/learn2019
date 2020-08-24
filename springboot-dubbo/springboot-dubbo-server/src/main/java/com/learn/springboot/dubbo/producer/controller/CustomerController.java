package com.learn.springboot.dubbo.producer.controller;

import com.learn.spring.dubbo.common.api.enums.StatusCode;
import com.learn.spring.dubbo.common.api.response.BaseResponse;
import com.learn.springboot.dubbo.producer.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * autor:liman
 * createtime:2020/8/24
 * comment:客户端controller 测试
 */
@RestController
@Slf4j
@RequestMapping("customer")
public class CustomerController {

    @Autowired
    private ICustomerService customerService;


    @RequestMapping(value="detail/{id}",method=RequestMethod.GET)
    public BaseResponse customerDetail(@PathVariable Integer id){
        log.info("开始查询客户详细信息，参数为:{}",id);
        if(id == null || id<0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(customerService.getCustomerInfo(id));
        }catch (Exception e){
            log.error("查询客户详情出现异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
