package com.learn.springboot.dubbo.producer.controller;

import com.learn.spring.dubbo.common.api.enums.StatusCode;
import com.learn.spring.dubbo.common.api.request.CustomerRequest;
import com.learn.spring.dubbo.common.api.request.IdEntityRequest;
import com.learn.spring.dubbo.common.api.response.BaseResponse;
import com.learn.springboot.dubbo.producer.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
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

    /**
     * 新增客户
     * @param customerRequest
     * @return
     */
    @RequestMapping(value="addCustomer",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse addCustomer(@RequestBody CustomerRequest customerRequest){
        log.info("开始新增客户信息,参数为：{}",customerRequest);
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try{
            customerService.saveCustomer(customerRequest);
        }catch (Exception e){
            log.error("新增客户信息出现异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 更新客户信息
     * @param customerRequest
     * @return
     */
    @RequestMapping(value="updateCustomer",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateCustomer(@RequestBody CustomerRequest customerRequest){
        log.debug("开始更新客户-请求体：{} ",customerRequest);

        if (customerRequest.getId()==null || customerRequest.getId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            customerService.updateCustomer(customerRequest);

        }catch (Exception e){
            log.error("更新客户-发生异常：request={} ",customerRequest,e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 删除客户
     * @return
     */
    @RequestMapping(value = "deleteCustomer",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse update(@RequestBody IdEntityRequest request){
        log.debug("删除客户-请求体：{} ",request);

        if (request.getId()==null || request.getId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            customerService.deleteCustomer(request);

        }catch (Exception e){
            log.error("删除客户-发生异常：request={} ",request,e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

}
