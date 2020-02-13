package com.learn.springbootjvm;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 *-Xmx15M -Xms15M -Xmn15M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+UseG1GC -Xloggc:F:\G1-gc.log
 *
 *-Xmx25M -Xms25M -Xmn25M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+UseG1GC -Xloggc:F:\G1-bigger-gc.log
 *
 * -Xmx25M -Xms25M -Xmn25M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+UseG1GC -XX:MaxGCPauseMillis=20 -XX:+PrintGCApplicationStoppedTime -Xloggc:F:\G1-stoptime-gc.log
 *
 * -Xmx25M -Xms25M -Xmn25M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:MaxGCPauseMillis=15 -XX:+UseG1GC -Xloggc:F:\G1-stoptime-02-gc.log
 *
 * -Xmx25M -Xms25M -Xmn25M -XX:+PrintGCDetails -XX:+PrintGCTimeStamps -XX:+PrintGCDateStamps -XX:+UseG1GC -XX:MaxGCPauseMillis=15 -XX:+PrintGCApplicationStoppedTime -Xloggc:F:\G1-stoptime-02-gc.log
 */
@SpringBootApplication
public class SpringbootJvmApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootJvmApplication.class, args);
    }

}
