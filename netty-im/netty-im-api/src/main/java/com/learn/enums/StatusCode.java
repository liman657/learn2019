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

    UserNamePasswordNotBlank(50000, "账户密码不能为空!"),
    AccessTokenNotBlank(50001, "accessToken必填，请在请求头header中塞入该字段"),
    User_Not_Exist(50002, "用户不存在"),
    NOT_YOURSELF(50003, "不能添加你自己"),
    ALREADY_FRIENDS(50004, "该用户已经是你的好友"),

    TokenValidateExpireToken(60001, "Token已过期"),
    TokenValidateCheckFail(60002, "Token验证失败"),

    AccessTokenNotExist(70001, "Token不存在-请重新登录!"),
    AccessTokenInvalidate(70002, "无效的Token!"),

    AccessTokenNotExistRedis(80001, "Token不存在或已经过期-请重新登录!"),

    AccessSessionNotExist(90001, "用户没登录或登录Session已经过期-请重新登录!"),

    LoginFail(100000, "用户名或密码不正确，登录失败！"),
    CurrUserHasNotPermission(100001, "当前用户没有权限访问该资源或者操作！"),
    CurrUserNotLogin(100002, "当前用户没有登录，请先进行登录！");

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
