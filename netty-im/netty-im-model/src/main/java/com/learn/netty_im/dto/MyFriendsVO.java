package com.learn.netty_im.dto;

import lombok.Data;

@Data
public class MyFriendsVO {
    //好友userId
    private String friendUserId;
    //好友通信账号
    private String friendUsername;
    //好友的头像地址
    private String friendFaceImage;
    //好友的昵称
    private String friendNickname;
    
}