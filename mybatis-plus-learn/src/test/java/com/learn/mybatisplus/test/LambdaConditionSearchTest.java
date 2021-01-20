package com.learn.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.additional.query.impl.LambdaQueryChainWrapper;
import com.learn.mybatisplus.dao.UserMapper;
import com.learn.mybatisplus.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/9
 * comment: Condition查询测试
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class LambdaConditionSearchTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void lambdaConditionSearchTest() {
//        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new QueryWrapper<UserEntity>().lambda();
//        LambdaQueryWrapper<UserEntity> lambdaQueryWrapper = new LambdaQueryWrapper<UserEntity>();
        LambdaQueryWrapper<UserEntity> userEntityLambdaQueryWrapper = Wrappers.<UserEntity>lambdaQuery();
        //避免了列名输错
        LambdaQueryWrapper<UserEntity> lambdaResult = userEntityLambdaQueryWrapper
                .like(UserEntity::getName, "雨").lt(UserEntity::getAge, 40);
        List<UserEntity> lambdaSelectResult = userMapper.selectList(lambdaResult);
        lambdaSelectResult.forEach(System.out::println);
    }

    /**
     * 3.0.7新增的lambda表达式
     */
    @Test
    public void newLambdaConditionSearchTest() {
        List<UserEntity> newLambdaQueryResult = new LambdaQueryChainWrapper<UserEntity>(userMapper)
                .like(UserEntity::getName, "雨")
                .ge(UserEntity::getAge, 20)
                .list();
        newLambdaQueryResult.forEach(System.out::println);
    }

}
