package com.learn.springboot.dubbo.producer.service;

import com.learn.spring.dubbo.common.api.request.CustomerRequest;
import com.learn.springboot.dubbo.model.entity.Customer;

/**
 * autor:liman
 * createtime:2020/8/24
 * comment:
 */
public interface ICustomerService {

    Customer getCustomerInfo(Integer id) throws Exception;

    Integer saveCustomer(CustomerRequest request) throws Exception;

    Integer updateCustomer(CustomerRequest request) throws Exception;
}
