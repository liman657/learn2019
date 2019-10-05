package com.learn.starter.format;

import com.learn.starter.format.autoconfiguration.HelloProperties;

/**
 * autor:liman
 * createtime:2019/9/29
 * comment:
 */
public class HelloFormatTemplate {

    private FormatProcessor formatProcessor;

    private HelloProperties helloProperties;

    public HelloFormatTemplate(FormatProcessor formatProcessor,HelloProperties helloProperties) {
        this.formatProcessor = formatProcessor;
        this.helloProperties = helloProperties;
    }

    public <T> String doFormat(T obj){
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Execute format : ").append("<br/>");
        stringBuilder.append("Obj format result :").append(formatProcessor.format(obj)).append("<br/>");
        stringBuilder.append("properties Infos : ").append(helloProperties.getInfos()).append("<br/>");
        stringBuilder.append("other properties info : ").append(helloProperties.getTestPropertiesConfig()).append("<br/>");
        return stringBuilder.toString();
    }
}
