package com.learn.netty_im.dao;

import com.learn.netty_im.domain.TUsers;
import java.util.List;

public interface TUsersMapper {
    int deleteByPrimaryKey(String id);

    int insert(TUsers record);

    TUsers selectByPrimaryKey(String id);

    List<TUsers> selectAll();

    int updateByPrimaryKey(TUsers record);
}