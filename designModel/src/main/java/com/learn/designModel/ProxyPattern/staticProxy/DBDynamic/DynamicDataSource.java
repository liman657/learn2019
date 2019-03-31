package com.learn.designModel.ProxyPattern.staticProxy.DBDynamic;

/**
 * autor:liman
 * comment:根据ThreadLocal进行切换数据源
 */
public class DynamicDataSource {

    private final static String DEFAULT_DATASOURCE = "DB_DEFAULT";

    private final static ThreadLocal<String> local = new ThreadLocal<>();

    private DynamicDataSource(){}

    public static String get(){
        return local.get();
    }

    public static void set(String dataSourceName){
        local.set(dataSourceName);
    }

    public static void set(int year){
        local.set("DB_"+year);
    }

    public static void restore(){
        local.set(DEFAULT_DATASOURCE);
    }

}
