package com.learn.netty_im.pojo.bo;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class FaceImgBO {
    private String userId;
    private String username;
    private String faceData;
}
