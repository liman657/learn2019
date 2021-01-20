package com.learn.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.learn.mybatisplus.dao.UserMapper;
import com.learn.mybatisplus.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/10
 * comment: 自定义SQL查询测试
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class SelfSqlSearchTest {
    @Autowired
    private UserMapper userMapper;

    @Test
    public void lambdaConditionSearchTest() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40);
        List<UserEntity> selfSqlResult = userMapper.selectSelfSQL(queryWrapper);
        selfSqlResult.forEach(System.out::println);
    }
}
