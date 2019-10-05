package com.learn.starter.format.autoconfiguration;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Map;

import static com.learn.starter.format.autoconfiguration.HelloProperties.SPI_HELLO_TEST_PREFIX;

/**
 * autor:liman
 * createtime:2019/9/29
 * comment:
 */
@ConfigurationProperties(prefix = SPI_HELLO_TEST_PREFIX)
public class HelloProperties {

    public static final String SPI_HELLO_TEST_PREFIX = "hello.spi.test";

    private Map<String,Object> infos;

    public Map<String, Object> getInfos() {
        return infos;
    }

    public void setInfos(Map<String, Object> infos) {
        this.infos = infos;
    }
}
