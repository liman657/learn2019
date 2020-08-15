package com.learn.springboot.redis.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * comment:发放红包的金额（发红包记录）
 */
@Data
public class RedRecord implements Serializable{
    private Integer id;

    private Integer userId;

    private String redPacket;

    private Integer total;

    private BigDecimal amount;

    private Byte isActive=1;

    private Date createTime;

}