package com.learn.rabbitmq.util;

import java.util.ResourceBundle;

/**
 * @Author: qingshan
 * @Date: 2018/11/16 15:34
 */
public class ResourceUtil {
    private static final ResourceBundle resourceBundle;

    static{
        resourceBundle = ResourceBundle.getBundle("config");
    }

    public static String getKey(String key){
        return resourceBundle.getString(key);
    }

}
