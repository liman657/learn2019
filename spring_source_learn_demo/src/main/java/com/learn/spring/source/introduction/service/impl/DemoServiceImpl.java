package com.learn.spring.source.introduction.service.impl;

import com.learn.spring.source.introduction.dao.DemoDao;
import com.learn.spring.source.introduction.dao.impl.DemoDaoImpl;
import com.learn.spring.source.introduction.factory.BeanFactory;
import com.learn.spring.source.introduction.service.DemoService;

import java.util.List;

/**
 * autor:liman
 * createtime:2020/9/2
 * comment:
 */
public class DemoServiceImpl implements DemoService {

//    private DemoDao demoDao = new DemoDaoImpl();

//    private DemoDao demoDao = BeanFactory.getDemoDao();

    private DemoDao demoDao = (DemoDao) BeanFactory.getBean("demoDao");
    @Override
    public List<String> findAll() {
        return demoDao.findAll(); //类之间的依赖关系，紧耦合
    }

}
