package com.learn.mybatisplus.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.learn.mybatisplus.dao.UserMapper;
import com.learn.mybatisplus.entity.UserEntity;
import com.learn.mybatisplus.service.UserService;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2021/1/17
 * comment:
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper,UserEntity> implements UserService {
}
