package com.learn.netty.objserial.messagepack;

import lombok.Data;
import lombok.ToString;
import org.msgpack.annotation.Message;

/**
 * autor:liman
 * createtime:2020/9/23
 * comment:
 */
@Message//这个注解表示这个实体需要交给MsgPack实例化
@Data
@ToString
public class UserInfo {
    private String name;
    private int age;
}
