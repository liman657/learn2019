package com.learn.netty_im.mapper;



import com.learn.netty_im.dto.FriendRequestVO;

import java.util.List;

public interface TUsersMapperConsumer {

    public List<FriendRequestVO> queryFriendRequestList(String acceptUserId);

}