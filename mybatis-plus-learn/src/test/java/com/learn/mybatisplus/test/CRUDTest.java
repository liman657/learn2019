package com.learn.mybatisplus.test;

import com.baomidou.mybatisplus.core.conditions.query.Query;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.learn.mybatisplus.dao.UserMapper;
import com.learn.mybatisplus.entity.UserEntity;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/9
 * comment: 2-1 BaseMapper的CRUD操作
 */
@SpringBootTest
@Slf4j
@RunWith(SpringRunner.class)
public class CRUDTest {

    @Autowired
    private UserMapper userMapper;

    /**
     * 新增数据测试
     */
    @Test
    public void createTest() {
        UserEntity userEntity = new UserEntity();
        userEntity.setAge(18);
        userEntity.setCreateTime(LocalDateTime.now());
        userEntity.setEmail("insertUser@mp.com");
        userEntity.setName("刘晓明");
        userEntity.setManagerId(1088248166370832385L);
        //返回的是影响记录数
        int insertCount = userMapper.insert(userEntity);
        log.info("插入数据条数:{}", insertCount);
    }


    //=============查询 start==============//
    @Test
    public void selectByIdTest() {
        Long id = 1087982257332887553L;
        UserEntity userEntity = userMapper.selectById(id);
        log.info("user id:{} 实体为:{}", id, userEntity);
    }

    @Test
    public void selectBatchIdsTest() {
        List<Long> ids = Arrays.asList(1087982257332887553L, 1088248166370832385L);
        List<UserEntity> userEntities = userMapper.selectBatchIds(ids);
        log.info("selectBatchIds的结果为:{}", userEntities);
    }

    @Test
    public void selectByMapTest() {
        /**
         * key - value
         * key是数据库中的列名，不是实体中的属性名
         */
        Map<String, Object> params = new HashMap<>();
        params.put("name", "王天风");
        params.put("age", 25);
        List<UserEntity> userEntities = userMapper.selectByMap(params);
        log.info("map 查询结果为:{}", userEntities);
    }

    @Test
    public void selectByWrapper() {
        /**
         * 1、名字中包含雨并且年龄小于40
         * 	name like '%雨%' and age<40
         */
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>();//直接实例化
        QueryWrapper<UserEntity> query = Wrappers.<UserEntity>query(); //静态方法实例化
        queryWrapper.like("name", "雨").le("age", 40);

        List<UserEntity> userEntities = userMapper.selectList(queryWrapper);
        log.info("wrapper user list:{}", userEntities);


        /**
         * 2、名字中包含雨年并且龄大于等于20且小于等于40并且email不为空
         *  name like '%雨%' and age between 20 and 40 and email is not null
         */
        queryWrapper.like("name", "%雨%").between("age", 20, 31).isNotNull("email");
        List<UserEntity> userEntitiesTitle2 = userMapper.selectList(queryWrapper);
        log.info("title 2 result:{}", userEntitiesTitle2);

        /**
         * 3、名字为王姓或者年龄大于等于25，按照年龄降序排列，年龄相同按照id升序排列
         *    name like '王%' or age>=25 order by age desc,id asc
         */
        query.likeRight("name", "王").or().ge("age", 25).orderByDesc("age").orderByAsc("id");
        List<UserEntity> userEntitiesTitle3 = userMapper.selectList(query);
        log.info("title 3 result:{}", userEntitiesTitle3);
    }

    @Test
    public void selectByWrapper2() {
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>();//直接实例化

        /**
         * 4、创建日期为2019年2月14日并且直属上级为名字为王姓
         * date_format(create_time,'%Y-%m-%d')='2019-02-14' and manager_id in (select id from user where name like '王%')
         *
         * 这个也行，但是会有SQL注入的风险
         * queryWrapper.apply("date_format(create_time,'%Y-%m-%d')= 2019-02-14")
         */
        queryWrapper.apply("date_format(create_time,'%Y-%m-%d')={0}", "2019-02-14")
                .inSql("manager_id", "select id from user where name like '王%'");
        List<UserEntity> userEntitiesTitle4 = userMapper.selectList(queryWrapper);
        log.info("mysql 日期函数，复合查询结果为:{}", userEntitiesTitle4);

        /**
         * 5、名字为王姓并且（年龄小于40或邮箱不为空）
         * name like '王%' and (age<40 or email is not null)
         * and方法接受一个Function的函数式接口
         */
//        queryWrapper.likeRight("name","王").and(wq->wq.lt("age",41).or().isNotNull("email"));
        //用了apply queryWrapper需要重新实例化
        QueryWrapper<UserEntity> queryNew = Wrappers.<UserEntity>query(); //静态方法实例化
        queryNew.likeRight("name", "王").and(wq -> wq.lt("age", 41).or().isNotNull("email"));
        List<UserEntity> userEntitiesTitle5 = userMapper.selectList(queryNew);
        log.info("and 函数实例结果为:{}", userEntitiesTitle5);

        /**
         * 6、名字为王姓或者（年龄小于40并且年龄大于20并且邮箱不为空）
         * name like '王%' or (age<40 and age>20 and email is not null)
         */
        QueryWrapper<UserEntity> querySix = Wrappers.<UserEntity>query(); //静态方法实例化
        querySix.likeRight("name", "王").or(wq -> wq.lt("age", 40)
                .gt("age", 20).isNotNull("email"));
        List<UserEntity> userEntityListSix = userMapper.selectList(querySix);
        log.info("and 函数实例结果为:{}", userEntityListSix);
    }

    @Test
    public void selectByWrapper3() {
        /**
         * 7、（年龄小于40或邮箱不为空）并且名字为王姓
         *  (age<40 or email is not null) and name like '王%'
         */
        QueryWrapper<UserEntity> queryWrapper = new QueryWrapper<UserEntity>();
//        queryWrapper.or(wp->wp.lt("age",40).or().isNotNull("email"))
////                .and(wq->wq.likeRight("name","王%"));
        queryWrapper.nested(wp -> wp.lt("age", 40).or().isNotNull("email")).likeRight("name", "王%");
        List<UserEntity> userEntitiesResult7 = userMapper.selectList(queryWrapper);
        log.info("第7个集合查询结果为:{}", userEntitiesResult7);

        /**
         * 8、年龄为30、31、34、35
         * age in (30、31、34、35)
         */
        List<Integer> ages = Arrays.asList(30, 31, 34, 35);
        QueryWrapper<UserEntity> queryAgeInWrapper = new QueryWrapper<UserEntity>();
        queryAgeInWrapper.in("age", ages);
        List<UserEntity> userAgeInResult = userMapper.selectList(queryAgeInWrapper);
        log.info("age in result:{}", userAgeInResult);

        /**
         * 9、只返回满足条件的其中一条语句即可
         * limit 1
         */
        QueryWrapper<UserEntity> queryLimitWrapper = new QueryWrapper<UserEntity>();
        queryLimitWrapper.last("limit 1");//无视SQL优化，有SQL注入的风险，慎用
        List<UserEntity> userEntityListLimit = userMapper.selectList(queryLimitWrapper);
        log.info("user limit result:{}", userEntityListLimit);

    }

    /**
     * select 不查出所有的列
     */
    @Test
    public void selectNotAllColumnTest() {
        /**
         * 11、包含雨并且年龄小于40
         * select id,name
         * from user
         * where name like '%雨%' and age<40
         */
        QueryWrapper<UserEntity> queryNotAllWrapper = new QueryWrapper<UserEntity>();
        queryNotAllWrapper.select("id", "name").like("name", "雨").lt("age", 40);
        List<UserEntity> userEntities = userMapper.selectList(queryNotAllWrapper);
        log.info("not all column result:{}", userEntities);
    }

    @Test
    public void selectNotAllColumnTest2() {
        /**
         * 11、包含雨并且年龄小于40 ，只是不要 create_time 和 manager_id 两列
         * select id,name,age,email
         * from user
         * where name like '%雨%' and age<40
         */
        QueryWrapper<UserEntity> queryNotAllWrapper = new QueryWrapper<UserEntity>();
        queryNotAllWrapper.select(UserEntity.class, info -> !info.getColumn().equalsIgnoreCase("create_time")
                && !info.getColumn().equalsIgnoreCase("manager_id"));
        List<UserEntity> userEntities = userMapper.selectList(queryNotAllWrapper);
        log.info("not all column result2:{}", userEntities);
    }

}
