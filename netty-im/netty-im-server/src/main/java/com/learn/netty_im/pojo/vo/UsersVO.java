package com.learn.netty_im.pojo.vo;

import lombok.Data;

import java.util.List;

@Data
public class UsersVO {
    private String id;
    private String username;
    private String faceImage;
    private String faceImageBig;
    private String nickname;
    private String qrcode;
}