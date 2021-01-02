package com.liman.learn.pmp.model.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;
import java.util.Date;

//商品类别
@Data
@TableName("item_type")
public class ItemTypeEntity implements Serializable{

    @TableId
    private Integer typeId;

    @NotBlank(message = "类别编码不能为空!")
    private String typeCode;

    @NotBlank(message = "类别名称不能为空!")
    private String typeName;

    private Integer orderNum;

    private Byte status=1;

    private Date createTime;

    private Date updateTime;


}