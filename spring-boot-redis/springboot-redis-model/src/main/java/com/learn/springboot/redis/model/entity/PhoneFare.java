package com.learn.springboot.redis.model.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode
public class PhoneFare implements Serializable {
    private Integer id;

    @NotBlank(message = "手机号码不能为空！")
    private String phone;

    @NotNull(message = "充值金额不能为空！")
    private BigDecimal fare;

    private Byte isActive = 1;
}