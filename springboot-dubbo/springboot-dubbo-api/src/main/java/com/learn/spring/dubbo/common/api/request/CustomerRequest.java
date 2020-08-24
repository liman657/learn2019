package com.learn.spring.dubbo.common.api.request;

import lombok.Data;
import lombok.ToString;

import java.io.Serializable;

/**
 *
 */
@Data
@ToString
public class CustomerRequest implements Serializable {

    private Integer id;

    private String name;

    private String address;

    private String phone;

}
