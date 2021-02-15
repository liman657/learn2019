package com.learn.netty_im.websocket;

import lombok.Data;

import java.util.Date;

@Data
public class ChatMsg {
    private String msgId;

    private String sendUserId;

    private String acceptUserId;

    private String msg; //消息内容

    /**
     * 消息是否签收状态
     * 1：签收
     * 0：未签收
     */
    private Integer signFlag;

    /**
     * 发送请求的事件
     */
    private Date createTime;

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    /**
     * @return send_user_id
     */
    public String getSendUserId() {
        return sendUserId;
    }

    /**
     * @param sendUserId
     */
    public void setSendUserId(String sendUserId) {
        this.sendUserId = sendUserId;
    }

    /**
     * @return accept_user_id
     */
    public String getAcceptUserId() {
        return acceptUserId;
    }

    /**
     * @param acceptUserId
     */
    public void setAcceptUserId(String acceptUserId) {
        this.acceptUserId = acceptUserId;
    }

    /**
     * @return msg
     */
    public String getMsg() {
        return msg;
    }

    /**
     * @param msg
     */
    public void setMsg(String msg) {
        this.msg = msg;
    }

    /**
     * 获取消息是否签收状态
     * 1：签收
     * 0：未签收
     *
     * @return sign_flag - 消息是否签收状态
     * 1：签收
     * 0：未签收
     */
    public Integer getSignFlag() {
        return signFlag;
    }

    /**
     * 设置消息是否签收状态
     * 1：签收
     * 0：未签收
     *
     * @param signFlag 消息是否签收状态
     *                 1：签收
     *                 0：未签收
     */
    public void setSignFlag(Integer signFlag) {
        this.signFlag = signFlag;
    }

    /**
     * 获取发送请求的事件
     *
     * @return create_time - 发送请求的事件
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * 设置发送请求的事件
     *
     * @param createTime 发送请求的事件
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}