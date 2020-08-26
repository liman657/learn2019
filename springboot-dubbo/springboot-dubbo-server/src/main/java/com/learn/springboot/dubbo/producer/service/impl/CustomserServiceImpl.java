package com.learn.springboot.dubbo.producer.service.impl;

import com.learn.spring.dubbo.common.api.entity.Customer;
import com.learn.spring.dubbo.common.api.request.CustomerRequest;
import com.learn.spring.dubbo.common.api.request.IdEntityRequest;
import com.learn.springboot.dubbo.model.mapper.CustomerMapper;
import com.learn.springboot.dubbo.producer.service.ICustomerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.beanutils.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * autor:liman
 * createtime:2020/8/24
 * comment:
 */
@Service
@Slf4j
public class CustomserServiceImpl implements ICustomerService {

    @Autowired
    private CustomerMapper customerMapper;

    @Override
    public Customer getCustomerInfo(Integer id) throws Exception {
        Customer customer=customerMapper.selectByPrimaryKey(id);
        if (customer==null){
            throw new Exception("客户不存在!");
        }
        return customer;
    }

    @Override
    public Integer saveCustomer(CustomerRequest request) throws Exception {
        Customer entity=new Customer();
        BeanUtils.copyProperties(request,entity);
        entity.setId(null);
        customerMapper.insertSelective(entity);
        return entity.getId();
    }

    @Override
    public Integer updateCustomer(CustomerRequest request) throws Exception {
        Customer entity=customerMapper.selectByPrimaryKey(request.getId());
        if (entity!=null){
            BeanUtils.copyProperties(request,entity);
            entity.setUpdateTime(new Date());
            return customerMapper.updateByPrimaryKeySelective(entity);
        }
        return -1;
    }

    /**
     * 删除客户
     * @param request
     * @return
     * @throws Exception
     */
    @Override
    public Integer deleteCustomer(IdEntityRequest request) throws Exception {
        Customer entity=customerMapper.selectByPrimaryKey(request.getId());
        if (entity!=null){
            //customerMapper.deleteByPrimaryKey(request.getId());

            entity.setIsActive(0);
            entity.setUpdateTime(new Date());
            return customerMapper.updateByPrimaryKeySelective(entity);
        }else {
            throw new Exception("客户不存在!");
        }
    }
}
