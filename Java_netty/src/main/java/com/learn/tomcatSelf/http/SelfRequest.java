package com.learn.tomcatSelf.http;

import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.io.InputStream;

/**
 * autor:liman
 * createtime:2019/10/28
 * comment:
 */
@Slf4j
public class SelfRequest {

    private String url;
    private String method;

    /**
     * 构造函数中，读取InputStream的内容
     * ，并完成url和method的初始化
     * @param in
     */
    public SelfRequest(InputStream in) throws IOException {
        String content = "";
        byte[] buff = new byte[1024];
        int len = 0;
        if((len = in.read(buff))>0){
            content = new String(buff,0,len);
            log.info("获得的客户端初始化信息:{}",content);
        }

        //处理HTTP报文
        String line = content.split("\\n")[0];
        String[] arr = line.split("\\s");

        this.method = arr[0];
        this.url = arr[1].split("\\?")[0];
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public String getUrl() {
        return url;
    }
}
