package com.learn.springboot.redis.model.mapper;

import com.learn.springboot.redis.model.entity.PhoneFare;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Set;

public interface PhoneFareMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(PhoneFare record);

    int insertSelective(PhoneFare record);

    PhoneFare selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(PhoneFare record);

    int updateByPrimaryKey(PhoneFare record);

    Set<PhoneFare> sortFareByPhone(@Param("isAsc") Integer isAsc);

    List<PhoneFare> getAllSortFares();
}