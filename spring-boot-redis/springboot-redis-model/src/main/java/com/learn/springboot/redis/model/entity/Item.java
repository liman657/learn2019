package com.learn.springboot.redis.model.entity;

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

//import javax.validation.constraints.NotBlank;
import java.io.Serializable;
import java.util.Date;

@Data
@ToString
public class Item implements Serializable{
    private Integer id;

    @NotBlank(message = "商品编码不能为空！")
    private String code;

    @NotBlank(message = "商品名称不能为空！")
    private String name;

    private Date createTime;

}