package com.learn.springauthserver.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * autor:liman
 * createtime:2019/12/15
 * comment:
 */
@Data
public class UpdatePsdDto implements Serializable {

    @NotBlank(message="旧密码不能为空")
    private String oldPassword;

    @NotBlank(message="新密码不能为空")
    private String newPassword;

}
