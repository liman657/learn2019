package com.learn.spring.source.introduction.dao.impl;

import com.learn.spring.source.introduction.dao.DemoDao;

import java.util.Arrays;
import java.util.List;

/**
 * autor:liman
 * createtime:2020/9/2
 * comment:
 */
public class DemoDaoImpl implements DemoDao {
    @Override
    public List<String> findAll() {
        // 此处应该是访问数据库的操作，用临时数据代替
        return Arrays.asList("aaa", "bbb", "ccc");
    }
}
