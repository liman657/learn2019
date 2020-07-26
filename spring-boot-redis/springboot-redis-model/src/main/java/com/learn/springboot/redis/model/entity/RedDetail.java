package com.learn.springboot.redis.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

@Data
public class RedDetail implements Serializable{

    private Integer id;

    private Integer recordId;

    private BigDecimal amount;

    private Byte isActive=1;

    private Date createTime;

}