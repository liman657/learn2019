package com.learn.springboot.redis.model.entity;

import lombok.Data;
import lombok.ToString;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@ToString
public class Product implements Serializable{
    private Integer id;

    @NotBlank(message = "商品名称不能为空")
    private String name;

    @NotNull(message = "所属商户id不能为空")
    private Integer userId;

    private Integer scanTotal=0;

    private Byte isActive=1;

}