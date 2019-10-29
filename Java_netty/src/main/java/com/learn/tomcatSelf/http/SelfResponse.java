package com.learn.tomcatSelf.http;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.OutputStream;

/**
 * autor:liman
 * createtime:2019/10/28
 * comment:
 */
@Slf4j
public class SelfResponse {

    private OutputStream outputStream;

    public SelfResponse(OutputStream out) {
        this.outputStream = out;//初始化outputstream
    }


    public void write(String s) throws IOException {
        StringBuilder stringBuilder = new StringBuilder();
//        stringBuilder.append(s);//这样回写是不行的.

        //需要将返回报文封装成HTTP报文的格式才行
        stringBuilder.append("HTTP/1.1 200 OK\n")
                .append("Content-Type:text/html;\n")
                .append("\r\n")
                .append(s);

        log.info("返回报文\n:{}",stringBuilder.toString());

        outputStream.write(stringBuilder.toString().getBytes());


    }
}
