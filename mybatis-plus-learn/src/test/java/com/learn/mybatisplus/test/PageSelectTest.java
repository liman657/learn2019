package com.learn.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
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
public class PageSelectTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 分页查询方式一
     */
    @Test
    public void pageSelectMethod01() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",19);
        //初始化的两个参数，第一个当前页码，第二个每页的记录数
        Page<UserEntity> pageSizeEntity = new Page<>(1,2);

        IPage<UserEntity> pageResult = userMapper.selectPage(pageSizeEntity, queryWrapper);
        System.out.println("总页数:"+pageResult.getPages());
        System.out.println("总记录数:"+pageResult.getTotal());
        List<UserEntity> records = pageResult.getRecords();
        System.out.println("记录详情为:"+records);
    }

    /**
     * 分页查询方式二
     */
    @Test
    public void pageSelectMethod02() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",19);
        //初始化的两个参数，第一个当前页码，第二个每页的记录数
        Page<UserEntity> pageSizeEntity = new Page<>(1,2);

        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(pageSizeEntity, queryWrapper);
        System.out.println("总页数:"+mapIPage.getPages());
        System.out.println("总记录数:"+mapIPage.getTotal());
        List<Map<String, Object>> records = mapIPage.getRecords();
        System.out.println("记录详情为:"+records);
    }

    /**
     * 分页查询方式三
     * 只查询记录，不查询总记录条数
     */
    @Test
    public void pageSelectMethod03() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",19);
        //初始化的两个参数，第一个当前页码，第二个每页的记录数，第三个参数表示是否查询记录条数，默认为true
        Page<UserEntity> pageSizeEntity = new Page<>(1,2,false);

        IPage<Map<String, Object>> mapIPage = userMapper.selectMapsPage(pageSizeEntity, queryWrapper);
        System.out.println("总页数:"+mapIPage.getPages());
        System.out.println("总记录数:"+mapIPage.getTotal());
        List<Map<String, Object>> records = mapIPage.getRecords();
        System.out.println("记录详情为:"+records);
    }

    /**
     * 分页查询方式四
     * 多表联查的时候，上述两种方法失效
     */
    @Test
    public void pageSelectMethod04() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.ge("age",19);
        //初始化的两个参数，第一个当前页码，第二个每页的记录数，第三个参数表示是否查询记录条数
        Page<UserEntity> pageSizeEntity = new Page<>(1,2,true);

        IPage<UserEntity> userEntityIPage = userMapper.selectSelfPage(pageSizeEntity, queryWrapper);
        System.out.println("总页数:"+userEntityIPage.getPages());
        System.out.println("总记录数:"+userEntityIPage.getTotal());
        List<UserEntity> records = userEntityIPage.getRecords();
        System.out.println("记录详情为:"+records);
    }

}
