package com.learn.spring.dubbo.common.api.request;/**
 * Created by Administrator on 2018/12/9.
 */

import lombok.Data;
import lombok.ToString;
import org.hibernate.validator.constraints.NotBlank;

import java.io.Serializable;

/**
 * @Author:debug (SteadyJack)
 * @Date: 2018/12/9 16:04
 * @Link:QQ-1974544863
 **/
@Data
@ToString
public class TokenRequest implements Serializable{

    @NotBlank
    private String accessToken;

}