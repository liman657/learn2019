package com.learn.spring.dubbo.common.api.request;/**
 * Created by Administrator on 2018/11/25.
 */

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 * @Author:debug (SteadyJack)
 * @Date: 2018/11/25 11:29
 * @Link:QQ-1974544863
 **/
@Data
@ToString
public class IdEntityRequest implements Serializable{

    private Integer id;

}