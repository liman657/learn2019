package com.learn.springauthmodel.mapper;

import com.learn.springauthmodel.entity.User;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface UserMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(User record);

    User selectByPrimaryKey(Integer id);

    List<User> selectAll();

    int updateByPrimaryKey(User record);

    User selectByUserName(@Param("userName") String userName);

    User selectByUserId(@Param("userId") Integer userId);

    int updatePassword(@Param("userName") String userName, @Param("oldPassword") String oldPassword, @Param("newPassword") String newPassword);
}