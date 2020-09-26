package com.learn.netty.objserial;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * autor:liman
 * createtime:2020/9/21
 * comment:
 */
@Slf4j
@Data
@ToString
public class UserEntity implements Serializable {

    private static final long serialVersionUID = -1L;

    private String userName;
    private int userid;


}
