package com.learn.netty_im.mapper;

import com.learn.netty_im.domain.TMyFriends;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TMyFriendsMapper {
    int deleteByPrimaryKey(String id);

    int insert(TMyFriends record);

    TMyFriends selectByPrimaryKey(String id);

    List<TMyFriends> selectAll();

    int updateByPrimaryKey(TMyFriends record);

    TMyFriends selectMyFriendByUserIdAndFriendId(@Param("myId") String myId, @Param("friendId") String friendId);
}