package com.learn.springboot.dubbo.producer.service.dubbo;

import com.learn.spring.dubbo.common.api.entity.Customer;
import com.learn.spring.dubbo.common.api.enums.StatusCode;
import com.learn.spring.dubbo.common.api.request.CustomerRequest;
import com.learn.spring.dubbo.common.api.request.IdEntityRequest;
import com.learn.spring.dubbo.common.api.response.BaseResponse;
import com.learn.spring.dubbo.common.api.service.IDubboCustomerService;
import com.learn.springboot.dubbo.producer.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * autor:liman
 * createtime:2020/8/25
 * comment:
 */
@Service(loadbalance = "roundrobin")
@Slf4j
public class DubboCustomerServiceImpl implements IDubboCustomerService {

    @Autowired
    ICustomerService customerService;

    @Override
    public BaseResponse customerDetail(Integer id) {
        log.info("[dubbo]开始查询客户详细信息，参数为:{}",id);
        if(id == null || id<0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            response.setData(customerService.getCustomerInfo(id));
        }catch (Exception e){
            log.error("[dubbo]查询客户详情出现异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponse addCustomer(CustomerRequest customerRequest) {
        log.info("[dubbo]开始新增客户信息,参数为：{}",customerRequest);
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try{
            customerService.saveCustomer(customerRequest);
        }catch (Exception e){
            log.error("[dubbo]新增客户信息出现异常，异常信息为:{}",e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponse updateCustomer(CustomerRequest customerRequest) {
        log.debug("[dubbo]开始更新客户-请求体：{} ",customerRequest);

        if (customerRequest.getId()==null || customerRequest.getId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            customerService.updateCustomer(customerRequest);

        }catch (Exception e){
            log.error("[dubbo]更新客户-发生异常：request={} ",customerRequest,e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @Override
    public BaseResponse updateCustomer(IdEntityRequest request) {
        log.debug("[dubbo]删除客户-请求体：{} ",request);

        if (request.getId()==null || request.getId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            customerService.deleteCustomer(request);

        }catch (Exception e){
            log.error("[dubbo]删除客户-发生异常：request={} ",request,e.fillInStackTrace());
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
