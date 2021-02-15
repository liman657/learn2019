package com.learn.feign;

import java.io.Serializable;

/**
 * autor:liman
 * createtime:2021/2/1
 * comment:
 */
public class Contributor implements Serializable {

    public String login;
    public int contributions;

    Contributor() {
    }

    Contributor(String login, int contributions) {
        this.login = login;
        this.contributions = contributions;
    }

}
