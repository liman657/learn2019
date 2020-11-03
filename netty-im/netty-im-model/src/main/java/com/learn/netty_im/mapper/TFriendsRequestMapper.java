package com.learn.netty_im.mapper;

import com.learn.netty_im.domain.TFriendsRequest;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TFriendsRequestMapper {
    int deleteByPrimaryKey(String id);

    int insert(TFriendsRequest record);

    TFriendsRequest selectByPrimaryKey(String id);

    List<TFriendsRequest> selectAll();

    int updateByPrimaryKey(TFriendsRequest record);

    TFriendsRequest selectTFriendsRequestBySendUserIdAndAcceptUserId(@Param("sendUserId") String sendUserId,@Param("acceptUserId") String acceptUserId);
}