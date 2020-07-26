package com.learn.springboot.redis.model.dto;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotNull;

@Data
@ToString
public class RedPacketDto {

    //发红包者
    @NotNull(message = "当前用户id必填！")
    private Integer userId;

    //指定多少人抢
    @NotNull(message = "指定人数不能为空！")
    private Integer total;

    //指定总金额-单位为分
    @NotNull(message = "红包总金额必填！")
    private Integer amount;
}









































