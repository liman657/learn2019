package com.liman.learn.spi;

import java.sql.*;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * autor:liman
 * createtime:2020/9/30
 * comment:
 */
public class SelfSqlDriver implements Driver {

    public Connection connect(String url, Properties info) throws SQLException {
        System.out.println("自己实现的数据库驱动类");
        return null;
    }

    public boolean acceptsURL(String url) throws SQLException {
        return false;
    }

    public DriverPropertyInfo[] getPropertyInfo(String url, Properties info) throws SQLException {
        return new DriverPropertyInfo[0];
    }

    public int getMajorVersion() {
        return 0;
    }

    public int getMinorVersion() {
        return 0;
    }

    public boolean jdbcCompliant() {
        return false;
    }

    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;
    }
}
