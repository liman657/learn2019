package com.learn.springauthmodel.mapper;

import com.learn.springauthmodel.entity.Log;

import java.util.List;

public interface LogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Log record);

    Log selectByPrimaryKey(Integer id);

    List<Log> selectAll();

    int updateByPrimaryKey(Log record);
}