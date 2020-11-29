package com.learn.enums;

/**
 * autor:liman
 * createtime:2019/11/20
 * comment: 返回的状态码枚举值
 * 这个类是提供给门户，ios，安卓，微信商城用的
 * 门户接受此类数据后需要使用本类的方法转换成对于的数据类型格式（类，或者list）
 * 其他自行处理
 * 200：表示成功
 * 500：表示错误，错误信息在msg字段中
 * 501：bean验证错误，不管多少个错误都以map形式返回
 * 502：拦截器拦截到用户token出错
 * 555：异常抛出信息
 *
 */
public enum StatusCode {

    Success(200, "成功"),
    Fail(500, "服务器异常"),
    InvalidParams(401, "非法的参数!"),
    PassFriendRequest(601, "通过好友请求"),
    IngoreFriendRequest(600, "忽略好友请求"),

    LoginFail(100000, "用户名或密码不正确，登录失败！");

    private Integer code;
    private String msg;

    StatusCode(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
