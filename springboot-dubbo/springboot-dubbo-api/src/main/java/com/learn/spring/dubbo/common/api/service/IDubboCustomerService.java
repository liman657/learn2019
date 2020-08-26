package com.learn.spring.dubbo.common.api.service;

import com.learn.spring.dubbo.common.api.entity.Customer;
import com.learn.spring.dubbo.common.api.request.CustomerRequest;
import com.learn.spring.dubbo.common.api.request.IdEntityRequest;
import com.learn.spring.dubbo.common.api.response.BaseResponse;

/**
 * autor:liman
 * createtime:2020/8/25
 * comment:
 */
public interface IDubboCustomerService {

    public BaseResponse customerDetail(Integer id);

    public BaseResponse addCustomer(CustomerRequest customerRequest);

    public BaseResponse updateCustomer(CustomerRequest customerRequest);

    public BaseResponse updateCustomer(IdEntityRequest request);

}
