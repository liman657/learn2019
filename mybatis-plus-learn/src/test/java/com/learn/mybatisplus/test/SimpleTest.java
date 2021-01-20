package com.learn.mybatisplus.test;

import com.learn.mybatisplus.dao.UserMapper;
import com.learn.mybatisplus.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/9
 * comment: hello world 的测试
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class SimpleTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void testHelloworld(){
        //null 表示不加条件
        List<UserEntity> userEntityList = userMapper.selectList(null);
        Assert.assertEquals(5,userEntityList.size());
        log.info("所有的用户信息为:{}",userEntityList);
    }

}
