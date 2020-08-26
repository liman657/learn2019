package com.learn.spring.dubbo.common.api.request;/**
 * Created by Administrator on 2018/12/9.
 */

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author:debug (SteadyJack)
 * @Date: 2018/12/9 11:27
 * @Link:QQ-1974544863
 **/
@Data
@ToString
public class AppRequest implements Serializable{

    private String appId;

    private String appSecret;

    private String timestamp;

    private String randomParam;
}
























