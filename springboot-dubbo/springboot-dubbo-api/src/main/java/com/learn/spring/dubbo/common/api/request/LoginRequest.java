package com.learn.spring.dubbo.common.api.request;/**
 * Created by Administrator on 2018/11/30.
 */

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author:debug (SteadyJack)
 * @Date: 2018/11/30 21:35
 * @Link:QQ-1974544863
 **/
@Data
@ToString
public class LoginRequest implements Serializable{

    @NotBlank
    private String userName;

    @NotBlank
    private String password;

}