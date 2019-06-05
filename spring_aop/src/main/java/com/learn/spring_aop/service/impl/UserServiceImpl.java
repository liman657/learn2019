package com.learn.spring_aop.service.impl;

import com.learn.spring_aop.entity.User;
import com.learn.spring_aop.service.UserService;

/**
 * author:liman
 * createtime:2019/6/4
 * comment:
 */
public class UserServiceImpl implements UserService {

    private static User user = null;

    @Override
    public User createUser(String firstName, String lastName, int age) {
        user = new User();
        user.setFirstName(firstName);
        user.setLastName(lastName);
        user.setAge(20);
        return user;
    }

    @Override
    public User queryUser() {
        return user;
    }
}
