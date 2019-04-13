package com.learn.designModel.AdapterPattern.Self;

import com.learn.designModel.AdapterPattern.ResultMsg;

/**
 * autor:liman
 * comment: 真正的适配器，继承原有的类，同时实现新标准的接口
 */
public class SiginServiceAdapter extends SiginService implements ILoginForThird {
    @Override
    public void loginForQQ(String username, String password) {
        ResultMsg resultMsg = processLogin(username, password, QQLogin.class);
        System.out.println(resultMsg);
    }

    @Override
    public void loginForWechat(String username, String password) {
        ResultMsg resultMsg = processLogin(username, password, WechatLogin.class);
        System.out.println(resultMsg);
    }

    @Override
    public void loginForSina(String username, String password) {
        ResultMsg resultMsg = processLogin(username, password, SinaLogin.class);
        System.out.println(resultMsg);
    }

    @Override
    public void loginForTaobao(String username, String password) {
        ResultMsg resultMsg = processLogin(username, password, TaobaoLogin.class);
        System.out.println(resultMsg);
    }

    @Override
    public void loginForAaliPay(String username, String password) {
        ResultMsg resultMsg = processLogin(username, password, AaliLogin.class);
        System.out.println(resultMsg);
    }

    /**
     * 优化后的登录方法，可以利用反射获取其他第三方账号登录方式
     * @param username
     * @param password
     * @param clazz
     * @return
     */
    private ResultMsg processLogin(String username,String password , Class<? extends ILoginAdapter> clazz){
        try {
            ILoginAdapter loginAdapter = clazz.newInstance();
            if(loginAdapter.support(loginAdapter)){
                return loginAdapter.login(username,password);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

   /**
     * 兼容原来的登录流程
     * @param username
     * @param password
     */
    public void Login(String username,String password){
        super.login(username,password);
    }
}
