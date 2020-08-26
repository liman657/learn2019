package com.learn.spring.dubbo.common.api.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class Customer implements Serializable {
    private Integer id;

    private String name;

    private String address;

    private String phone;

    private Integer isActive;

    private Date createTime;

    private Date updateTime;
}
