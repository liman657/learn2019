package com.learn.springboot.dubbo.producer.service;

import com.learn.spring.dubbo.common.api.entity.Customer;
import com.learn.spring.dubbo.common.api.request.CustomerRequest;
import com.learn.spring.dubbo.common.api.request.IdEntityRequest;

/**
 * autor:liman
 * createtime:2020/8/24
 * comment:
 */
public interface ICustomerService {

    Customer getCustomerInfo(Integer id) throws Exception;

    Integer saveCustomer(CustomerRequest request) throws Exception;

    Integer updateCustomer(CustomerRequest request) throws Exception;

    Integer deleteCustomer(IdEntityRequest request) throws Exception;
}
