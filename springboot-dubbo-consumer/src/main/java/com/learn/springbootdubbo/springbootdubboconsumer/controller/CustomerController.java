package com.learn.springbootdubbo.springbootdubboconsumer.controller;

import com.learn.spring.dubbo.common.api.enums.StatusCode;
import com.learn.spring.dubbo.common.api.request.CustomerRequest;
import com.learn.spring.dubbo.common.api.request.IdEntityRequest;
import com.learn.spring.dubbo.common.api.response.BaseResponse;
import com.learn.spring.dubbo.common.api.service.IDubboCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
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

    @Reference
    private IDubboCustomerService dubboCustomerService;


    @RequestMapping(value="detail/{id}",method=RequestMethod.GET)
    public BaseResponse customerDetail(@PathVariable Integer id){
        log.info("开始调用dubbo服务查询客户详细信息，参数为:{}",id);
        if(id == null || id<0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(dubboCustomerService.customerDetail(id));
        }catch (Exception e){
            log.error("调用dubbo服务查询客户详情出现异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
