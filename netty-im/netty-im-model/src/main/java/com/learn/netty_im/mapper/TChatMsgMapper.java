package com.learn.netty_im.mapper;

import com.learn.netty_im.domain.TChatMsg;

import java.util.List;

public interface TChatMsgMapper {
    int deleteByPrimaryKey(String id);

    int insert(TChatMsg record);

    TChatMsg selectByPrimaryKey(String id);

    List<TChatMsg> selectAll();

    int updateByPrimaryKey(TChatMsg record);

    void batchUpdateMsgSigned(List<String> msgIdList);
}