package com.learn.mybatisplus.test;

import com.learn.mybatisplus.dao.ARUserMapper;
import com.learn.mybatisplus.dao.UserMapper;
import com.learn.mybatisplus.entity.ARUserEntity;
import com.learn.mybatisplus.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;

/**
 * autor:liman
 * createtime:2021/1/10
 * comment:AR模式测试
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class ARModelTest {

    @Autowired
    private ARUserMapper aRuserMapper;

    @Test
    public void insertTest(){
        ARUserEntity userEntity = new ARUserEntity();
        userEntity.setAge(18);
        userEntity.setCreateTime(LocalDateTime.now());
        userEntity.setEmail("arInsert@mp.com");
        userEntity.setName("张超");
        userEntity.setManagerId(1088248166370832385L);
        //返回的是影响记录数
        boolean result = userEntity.insert();
        System.out.println("插入是否成功"+ result);
    }

    @Test
    public void searchTest(){
        ARUserEntity userEntity = new ARUserEntity();
        userEntity.setId(1087982257332887553l);
        ARUserEntity selectUserinfo = userEntity.selectById(userEntity);
//        ARUserEntity selectUserinfo = userEntity.selectById(1087982257332887553l);//或者直接这么设置id
        //查出来的是一个新对象，不是原对象，两者不等
        System.out.println(selectUserinfo == userEntity);//这里会输出false
        System.out.println(selectUserinfo);
    }

    @Test
    public void updateTest(){
        ARUserEntity userEntity = new ARUserEntity();
        userEntity.setId(1348228302317342722l);
        userEntity.setName("张超人");
        boolean updateResult = userEntity.updateById();
        System.out.println("是否更新成功的结果:"+updateResult);
    }

    @Test
    public void deleteTest(){
        ARUserEntity userEntity = new ARUserEntity();
        userEntity.setId(1348228302317342722l);
        //删除不存在的记录，这里是会返回成功的
        boolean deleteResult = userEntity.deleteById();
        System.out.println("是否删除成功的结果:"+deleteResult);
    }

    @Test
    public void insertOrUpdateTest(){
        ARUserEntity userEntity = new ARUserEntity();
        userEntity.setAge(18);
        userEntity.setCreateTime(LocalDateTime.now());
        userEntity.setEmail("arInsert@mp.com");
        userEntity.setName("张超");
        userEntity.setManagerId(1088248166370832385L);
        /**
         * 如果没有设置id，值，则update；如果有设id值，则根据id查出来然后update
         */
        boolean result = userEntity.insertOrUpdate();
        System.out.println("insertOrUpdate result"+ result);
    }

}
