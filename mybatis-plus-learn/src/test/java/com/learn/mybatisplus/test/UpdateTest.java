package com.learn.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.additional.update.impl.LambdaUpdateChainWrapper;
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
 * comment: 分页查询
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class UpdateTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询方式一
     */
    @Test
    public void updateByIdtest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1088248166370832385l);
        userEntity.setAge(26);
        userEntity.setEmail("update@test.com");
        int rows = userMapper.updateById(userEntity);
        System.out.println("影响记录数:"+rows);
    }

    @Test
    public void updateByWrapperTest(){
        /**
         * 这里的wrapper中的条件会出现在where语句中，实体的值会出现在set中
         */
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("name","李艺伟").eq("age",28);
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(18);
        int updateRows = userMapper.update(userEntity, updateWrapper);
        System.out.println("wrapper更新记录数为:"+updateRows);
    }

    @Test
    public void updateByWrapperEntity(){
        /**
         * 这里的实体中不为空的值会出现在where语句中，后面的userEntity不为空的才会出现在set语句中
         */
        UserEntity whereUserEntity = new UserEntity();
        whereUserEntity.setName("李艺伟");
        whereUserEntity.setAge(28);
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>(whereUserEntity);
        //如果同时存在这两者，则会出现多个and
        //updateWrapper.eq("name","李艺伟").eq("age",28);

        UserEntity userEntity = new UserEntity();
        userEntity.setAge(18);
        int updateRows = userMapper.update(userEntity, updateWrapper);
        System.out.println("更新记录数为:"+updateRows);
    }

    @Test
    public void updateSimpleSet(){
        /**
         * 这里不传更新的实体，直接通过wrapper对象的set方法指定列名进行更新
         */
        UpdateWrapper<UserEntity> updateWrapper = new UpdateWrapper<>();
        //如果同时存在这两者，则会出现多个and
        updateWrapper.eq("name","李艺伟").eq("age",18).set("age",29);

//        UserEntity userEntity = new UserEntity();
//        userEntity.setAge(18);
        int updateRows = userMapper.update(null,updateWrapper);
        System.out.println("更新记录数为:"+updateRows);
    }

    @Test
    public void updateLambdaQuery(){
        LambdaUpdateWrapper<UserEntity> updateWrapper = Wrappers.<UserEntity>lambdaUpdate();
        updateWrapper.eq(UserEntity::getName,"李艺伟").eq(UserEntity::getAge,29).set(UserEntity::getAge,18);
        int updateRows = userMapper.update(null, updateWrapper);
        System.out.println("更新记录数为:"+updateRows);
    }

    @Test
    public void updateLambdaQueryChain(){
        boolean updateRows = new LambdaUpdateChainWrapper<UserEntity>(userMapper)
                .eq(UserEntity::getName, "李艺伟").eq(UserEntity::getAge, 29).set(UserEntity::getAge, 18).update();
        System.out.println("是否更新成功:"+updateRows);
    }

}
