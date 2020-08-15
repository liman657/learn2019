package com.learn.springboot.redis.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;


/**
 * 抢红包的记录
 */
@Data
public class RedRobRecord implements Serializable{
    private Integer id;

    private Integer userId;

    private String redPacket;

    private BigDecimal amount;

    private Date robTime;

    private Byte isActive=1;

}