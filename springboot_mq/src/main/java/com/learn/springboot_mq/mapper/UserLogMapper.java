package com.learn.springboot_mq.mapper;


import com.learn.springboot_mq.entity.UserLog;
import org.hibernate.validator.constraints.pl.REGON;
import org.springframework.stereotype.Repository;

@Repository
public interface UserLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLog record);

    int insertSelective(UserLog record);

    UserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLog record);

    int updateByPrimaryKey(UserLog record);
}