package com.learn.designModel.AdapterPattern;

/**
 * autor:liman
 * comment:
 */
public class PassportTest {

    public static void main(String[] args) {

        SiginService siginService = new SiginService();
        ResultMsg login = siginService.login("test", "test");
        System.out.println(login);

        IPassportForThird passportForThird = new PassportForThirdAdapter();
        ResultMsg resultMsg = passportForThird.loginForQQ("");
        System.out.println(resultMsg);
    }

}