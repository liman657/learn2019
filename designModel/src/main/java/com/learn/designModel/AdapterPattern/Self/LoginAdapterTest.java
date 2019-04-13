package com.learn.designModel.AdapterPattern.Self;

/**
 * autor:liman
 * comment:
 */
public class LoginAdapterTest {

    public static void main(String[] args) {
        SiginServiceAdapter serviceAdapter = new SiginServiceAdapter();
        serviceAdapter.loginForQQ("test","password");
    }

}
