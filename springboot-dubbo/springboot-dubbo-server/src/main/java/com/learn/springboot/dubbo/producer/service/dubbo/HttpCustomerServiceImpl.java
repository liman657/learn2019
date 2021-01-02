package com.learn.springboot.dubbo.producer.service.dubbo;

import com.learn.spring.dubbo.common.api.entity.Customer;
import com.learn.spring.dubbo.common.api.request.CustomerRequest;
import com.learn.spring.dubbo.common.api.request.IdEntityRequest;
import com.learn.spring.dubbo.common.api.response.BaseResponse;
import com.learn.spring.dubbo.common.api.service.IDubboCustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.dubbo.config.annotation.Service;

/**
 * autor:liman
 * createtime:2020/8/25
 * comment:
 */
@Service(protocol = "rest")
@Slf4j
public class HttpCustomerServiceImpl implements IDubboCustomerService {

//    @Override
//    public BaseResponse getCustomerInfo(Integer id) throws Exception {
//        log.info("http 请求进入,参数为:{}",id);
//        return null;
//    }

    @Override
    public BaseResponse customerDetail(Integer id) {
        return null;
    }

    @Override
    public BaseResponse addCustomer(CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public BaseResponse updateCustomer(CustomerRequest customerRequest) {
        return null;
    }

    @Override
    public BaseResponse updateCustomer(IdEntityRequest request) {
        return null;
    }
}
