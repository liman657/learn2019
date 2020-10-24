package com.learn.netty_im.mapper;

import com.learn.netty_im.domain.TUsers;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TUsersMapper {
    int deleteByPrimaryKey(String id);

    void insert(TUsers record);

    TUsers selectByPrimaryKey(String id);

    List<TUsers> selectAll();

    int updateByPrimaryKey(TUsers record);

    TUsers selectByUsernameAndPassword(@Param("username") String username, @Param("password") String password);
}