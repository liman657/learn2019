package com.learn.mybatisplus.dao;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Constants;
import com.learn.mybatisplus.entity.ARUserEntity;
import com.learn.mybatisplus.entity.UserEntity;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * autor:liman
 * createtime:2021/1/9
 * comment:这里就是自定义的Mapper，需要继承MybatisPlus提供的基础mapper
 */
public interface ARUserMapper extends BaseMapper<ARUserEntity> {

    /**
     * 自定义SQL查询
     */
//    @Select("select * from user ${ew.customSqlSegment}")
    List<ARUserEntity> selectSelfSQL(@Param(Constants.WRAPPER) Wrapper<ARUserEntity> wrapper);

    /**
     * 自定义分页查询方法
     */
    IPage<ARUserEntity> selectSelfPage(IPage<ARUserEntity> userPage, @Param(Constants.WRAPPER) Wrapper<ARUserEntity> wrapper);

}
