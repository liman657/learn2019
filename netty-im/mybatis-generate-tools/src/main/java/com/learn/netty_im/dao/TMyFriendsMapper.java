package com.learn.netty_im.dao;

import com.learn.netty_im.domain.TMyFriends;
import java.util.List;

public interface TMyFriendsMapper {
    int deleteByPrimaryKey(String id);

    int insert(TMyFriends record);

    TMyFriends selectByPrimaryKey(String id);

    List<TMyFriends> selectAll();

    int updateByPrimaryKey(TMyFriends record);
}