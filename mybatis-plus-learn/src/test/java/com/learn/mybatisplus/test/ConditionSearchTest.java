package com.learn.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.learn.mybatisplus.dao.UserMapper;
import com.learn.mybatisplus.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
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
public class ConditionSearchTest {

    @Autowired
    private UserMapper userMapper;

    @Test
    public void conditionSearchTest() {
        String name = "王";
        String email = "";
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
//        if (StringUtils.isNotEmpty(name)) {
//            queryWrapper.like("name", name);
//        }
//
//        if (StringUtils.isNotEmpty(email)) {
//            queryWrapper.like("email", email);
//        }

        queryWrapper.like(StringUtils.isNotEmpty(name), "name", name)
                .like(StringUtils.isNotEmpty(email), "email", email);

        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        log.info("condition search result:{}", userEntities);
    }

    /**
     * 对象作为查询条件
     */
    @Test
    public void searchEntityParamTest(){
        UserEntity paramUserEntity = new UserEntity();
        paramUserEntity.setName("张雨琪");
        paramUserEntity.setAge(31);

        //默认是等值比对，如果想利用对象查询的同时，也在属性上加入非等值的条件查询，就需要在Entity上加入TableField注解
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>(paramUserEntity);
        queryWrapper.like("name","雨");
        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        log.info("entity search result:{}",userEntities);
    }

    @Test
    public void searchAllEqTest(){
        Map<String,Object> params = new HashMap<>();
        params.put("name","张雨琪");
        params.put("age",null);

        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.allEq(params);//where 之后出现 age is null
        //queryWrapper.allEq(params,false);// 之后不会出现 age is null;
//        queryWrapper.allEq((k,v)->!k.equals("age"),params);//第一个参数根据条件过滤，符合条件的才会加入到查询条件里头
        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        log.info("allEq query result:{}",userEntities);
    }

    @Test
    public void otherSelectMethodTest(){
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.like("name","雨").lt("age",40);
        List<Map<String, Object>> selectMapsEntity = userMapper.selectMaps(queryWrapper);
        selectMapsEntity.forEach(System.out::println);
    }

    @Test
    public void selectCalResultTest(){
        /**
         * 按照直属上级分组，查询每组的平均年龄、最大年龄、最小年龄。
         * 并且只取年龄总和小于500的组。
         * select avg(age) avg_age,min(age) min_age,max(age) max_age
         * from user
         * group by manager_id
         * having sum(age) <500
         */
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<>();
        queryWrapper.select("avg(age) avg_age","min(age) min_age","max(age) max_age")
                .groupBy("manager_id").having("sum(age) < {0}",500);
        List<Map<String, Object>> selectMapsEntity = userMapper.selectMaps(queryWrapper);
        selectMapsEntity.forEach(System.out::println);

        //selectObjs
        QueryWrapper<UserEntity> queryObjsWrapper = new QueryWrapper<>();
        queryObjsWrapper.select("id","name").like("name","雨").lt("age",40);
        List<Object> objects = userMapper.selectObjs(queryObjsWrapper);
        objects.forEach(System.out::println);

        //selectCount 这种就不能设置select的列名了，底层会自动设置成count(1)
        QueryWrapper<UserEntity> queryCountWrapper = new QueryWrapper<>();
        queryCountWrapper.like("name","雨").lt("age",40);
        Integer count = userMapper.selectCount(queryCountWrapper);
        System.out.println(count);

        //selectOne 只能查询一个，如果有多条会报错
        QueryWrapper<UserEntity> queryOneWrapper = new QueryWrapper<>();
        queryOneWrapper.like("name","张雨琪").lt("age",40);
        UserEntity userEntity = userMapper.selectOne(queryOneWrapper);
        System.out.println(userEntity);
    }

}
