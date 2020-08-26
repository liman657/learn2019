package com.learn.springboot.dubbo.producer.service.dubbo;

import com.learn.spring.dubbo.common.api.entity.Customer;
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

    @Override
    public Customer getCustomerInfo(Integer id) throws Exception {
        log.info("http 请求进入,参数为:{}",id);
        return null;
    }
}
