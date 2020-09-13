package com.learn.springauthmodel.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * autor:liman
 * createtime:2019/12/11
 * comment:需要返回给前端的Modle实体
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthTokenModel implements Serializable {

    private String accessToken;

    private Long accessExpire;

}
