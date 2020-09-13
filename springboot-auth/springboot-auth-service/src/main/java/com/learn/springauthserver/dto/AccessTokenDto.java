package com.learn.springauthserver.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * autor:liman
 * createtime:2019/12/11
 * comment:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
/**
 * 生成token的元素，生成的最初的token，需要用到这些元素
 */
public class AccessTokenDto implements Serializable {

    private Integer userId; //userId
    private String userName;    //userName
    private Long timestamp;     //时间戳
    private String randomStr;   //随机串
    //    private String projectCode; //项目编码
    private Long expire;

}
