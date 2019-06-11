package com.learn.transaction;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * autor:liman
 * createtime:2019/6/10
 * comment:
 */
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public void addStu() {

    }

    @Override
    public void update() {

    }

    @Override
    public List select() {
        return null;
    }

    @Override
    public int deleteData() {
        return 0;
    }
}
