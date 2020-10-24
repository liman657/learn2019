package com.learn.netty_im.dao;

import com.learn.netty_im.domain.TFriendsRequest;
import java.util.List;

public interface TFriendsRequestMapper {
    int deleteByPrimaryKey(String id);

    int insert(TFriendsRequest record);

    TFriendsRequest selectByPrimaryKey(String id);

    List<TFriendsRequest> selectAll();

    int updateByPrimaryKey(TFriendsRequest record);
}