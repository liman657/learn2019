package com.learn.spi;

import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.util.ServiceLoader;

/**
 * autor:liman
 * createtime:2020/9/30
 * comment:
 */
public class DriverSPIDemo {

    public static void main(String[] args) {
        ServiceLoader<Driver> drivers = ServiceLoader.load(Driver.class);
        for(Driver d:drivers){
            try {
//                Driver selfDriver = DriverManager.getDriver("test");
                d.connect("test",new Properties());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
