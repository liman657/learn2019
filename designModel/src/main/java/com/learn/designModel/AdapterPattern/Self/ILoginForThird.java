package com.learn.designModel.AdapterPattern.Self;

/**
 * autor:liman
 * comment: 新的登录标准，常见的第三方登录
 */
public interface ILoginForThird {

    /**
     * QQ账号登录
     * @param username
     * @param password
     */
    public void loginForQQ(String username,String password);

    /**
     * 微信账号登录
     * @param username
     * @param password
     */
    public void loginForWechat(String username,String password);

    /**
     * 新浪登录
     * @param username
     * @param password
     */
    public void loginForSina(String username,String password);

    /**
     * 淘宝登录
     * @param username
     * @param password
     */
    public void loginForTaobao(String username,String password);

    /**
     * 支付宝登录
     * @param username
     * @param password
     */
    public void loginForAaliPay(String username,String password);
}
