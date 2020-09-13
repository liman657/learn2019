package com.learn.springauthmodel.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

@Data
public class AuthToken implements Serializable {
    private Integer id;

    private Integer userId;

    private String accessToken;

    private Long accessExpire;

    private Long tokenTimestamp;

    private Byte isActive;

    private Date createTime;

    private Date updateTime;

    private static final long serialVersionUID = 1L;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken == null ? null : accessToken.trim();
    }

    public Long getAccessExpire() {
        return accessExpire;
    }

    public void setAccessExpire(Long accessExpire) {
        this.accessExpire = accessExpire;
    }

    public Long getTokenTimestamp() {
        return tokenTimestamp;
    }

    public void setTokenTimestamp(Long tokenTimestamp) {
        this.tokenTimestamp = tokenTimestamp;
    }

    public Byte getIsActive() {
        return isActive;
    }

    public void setIsActive(Byte isActive) {
        this.isActive = isActive;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", id=").append(id);
        sb.append(", userId=").append(userId);
        sb.append(", accessToken=").append(accessToken);
        sb.append(", accessExpire=").append(accessExpire);
        sb.append(", tokenTimestamp=").append(tokenTimestamp);
        sb.append(", isActive=").append(isActive);
        sb.append(", createTime=").append(createTime);
        sb.append(", updateTime=").append(updateTime);
        sb.append(", serialVersionUID=").append(serialVersionUID);
        sb.append("]");
        return sb.toString();
    }
}