package com.learn.response;


import com.learn.enums.StatusCode;

/**
 * autor:liman
 * createtime:2019/11/20
 * comment:
 * 统一返回消息的响应模型
 */
public class BaseResponse<T> {

    private Integer status;
    private String message;

    private T data;

    public BaseResponse(StatusCode statusCode) {
        this.status = statusCode.getCode();
        this.message = statusCode.getMsg();
    }

    public BaseResponse(StatusCode statusCode, T data) {
        this.status = statusCode.getCode();
        this.message = statusCode.getMsg();
        this.data = data;
    }

    public BaseResponse(Integer status, String message, T data) {
        this.status = status;
        this.message = message;
        this.data = data;
    }

    public BaseResponse(Integer status, String message) {
        this.status = status;
        this.message = message;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return "BaseResponse{" +
                "status=" + status +
                ", message='" + message + '\'' +
                ", data=" + data +
                '}';
    }
}
