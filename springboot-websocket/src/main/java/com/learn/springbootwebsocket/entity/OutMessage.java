package com.learn.springbootwebsocket.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Date;

/**
 * autor:liman
 * createtime:2020/10/26
 * comment:
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OutMessage {
    private String from;

    private String content;

    private Date time = new Date();


    public OutMessage(String content) {
        this.content = content;
    }
}
