package com.learn.spring_aop.service;

import com.learn.spring_aop.entity.User;

/**
 * author:liman
 * createtime:2019/6/4
 */
public interface UserService {

    User createUser(String firstName,String lastName,int age);

    User queryUser();

}
