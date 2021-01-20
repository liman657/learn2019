package com.learn.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learn.mybatisplus.dao.UserMapper;
import com.learn.mybatisplus.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/10
 * comment: 删除测试
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class DeleteTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void deleteByIdTest() {
        Long id = 1l;
        int deleteRows = userMapper.deleteById(id);
        System.out.println("删除的行数:"+deleteRows);
    }

    @Test
    public void deleteByMapTest(){
        Map<String,Object> columnMap = new HashMap<>();
        columnMap.put("name","test");
        columnMap.put("age",20);
        int deleteRows = userMapper.deleteByMap(columnMap);
        System.out.println("删除的行数:"+deleteRows);
    }

    @Test
    public void deleteByBatchIdsTest(){
        int deleteRows = userMapper.deleteBatchIds(Arrays.asList(1l, 2l, 3l));
        System.out.println("删除的行数:"+deleteRows);
    }

    @Test
    public void deleteByWrapperTest(){
        LambdaQueryWrapper<UserEntity> lambdaDeleteWrapper = Wrappers.<UserEntity>lambdaQuery();
        lambdaDeleteWrapper.ge(UserEntity::getAge,80);
        int deleteRows = userMapper.delete(lambdaDeleteWrapper);
        System.out.println("删除的行数:"+deleteRows);

    }

}
