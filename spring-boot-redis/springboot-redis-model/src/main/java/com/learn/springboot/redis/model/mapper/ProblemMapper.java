package com.learn.springboot.redis.model.mapper;


import com.learn.springboot.redis.model.entity.Problem;

import java.util.Set;

public interface ProblemMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Problem record);

    int insertSelective(Problem record);

    Problem selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Problem record);

    int updateByPrimaryKey(Problem record);

    Set<Problem> getAll();
}