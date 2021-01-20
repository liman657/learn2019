package com.learn.mybatisplus.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.time.LocalDateTime;

/**
 * autor:liman
 * createtime:2021/1/9
 * comment:
 */
@Data
@ToString
@TableName("user")
@EqualsAndHashCode(callSuper = false)
public class ARUserEntity extends Model<ARUserEntity> {

    //主键id
    private Long id;

    //姓名
    @TableField
    private String name;

    //
//    @TableField(condition = SqlCondition.EQUAL)
    @TableField(condition = "%s&lt;#{%s}") //等同于<
    private Integer age;

    //邮箱
    private String email;

    //直属上级ID
    private Long managerId;

    //创建时间
    private LocalDateTime createTime;

    @TableField(exist = false)
    private String remark;


}
