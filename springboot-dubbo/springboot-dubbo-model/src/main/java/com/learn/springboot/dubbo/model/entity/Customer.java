package com.learn.springboot.dubbo.model.entity;

import lombok.Data;

import java.util.Date;

@Data
public class Customer {
    private Integer id;

    private String name;

    private String address;

    private String phone;

    private Integer isActive;

    private Date createTime;

    private Date updateTime;
}
