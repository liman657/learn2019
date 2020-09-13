package com.learn.springauthserver.controller;

import com.learn.springauthapi.enums.StatusCode;
import com.learn.springauthapi.response.BaseResponse;
import com.learn.springauthserver.dto.UpdatePsdDto;
import com.learn.springauthserver.redis.RedisService;
import com.learn.springauthserver.utils.ValidatorUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * autor:liman
 * createtime:2019/12/15
 * comment:
 */
@RestController
@Slf4j
@RequestMapping("/redisAuth")
public class RedisAuthController {

    @Autowired
    private RedisService redisService;

    @RequestMapping(value = "/test", method = RequestMethod.GET)
    public BaseResponse testRedis(@RequestParam("redisValue") String redisValue) {
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try {
            redisService.testRedis(redisValue);
            redisService.testStringTemplate(redisValue);
        } catch (Exception e) {

        }
        return result;
    }

    /**
     * redis的登录认证
     *
     * @param userName
     * @param password
     * @return
     */
    @RequestMapping(value = "login", method = RequestMethod.POST)
    public BaseResponse login(@RequestParam String userName, @RequestParam String password) {
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try {
            if (StringUtils.isBlank(userName) || StringUtils.isBlank(password)) {
                return new BaseResponse(StatusCode.UserNamePasswordNotBlank);
            }
            result.setData(redisService.authAndCreateToken(userName, password));
            ;
        } catch (Exception e) {
            return new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "token/auth", method = RequestMethod.GET)
    public BaseResponse getAuthResource() {
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try {
            String redisAuthInfo = "this is redis auth info";
            result.setData(redisAuthInfo);
        } catch (Exception e) {
            result = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "token/unauth", method = RequestMethod.GET)
    public BaseResponse getUnAuthResource() {
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try {
            String redisAuthInfo = "[unauth] this is redis info";
            result.setData(redisAuthInfo);
        } catch (Exception e) {
            result = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "password/update",method=RequestMethod.POST)
    public BaseResponse updatePassword(@RequestHeader String accessToken, @RequestBody @Validated UpdatePsdDto updatePsdDto,
                                       BindingResult bindingResult) {
        BaseResponse result = new BaseResponse(StatusCode.Success);
        log.info("--token+redis修改密码");
        if (StringUtils.isBlank(accessToken)) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        String res = ValidatorUtils.checkResult(bindingResult);
        if (StringUtils.isNotBlank(res)) {
            return new BaseResponse(StatusCode.InvalidParams.getCode(), res);
        }
        try {
            redisService.updatePassword(accessToken, updatePsdDto);
        } catch (Exception e) {
            result = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        log.info("[修改密码]密码修改结束");
        return result;
    }

    @RequestMapping(value = "logout", method = RequestMethod.GET)
    public BaseResponse logout(@RequestHeader String accessToken) {
        log.info("[退出登录],token为:{}",accessToken);
        BaseResponse result = new BaseResponse(StatusCode.Success);
        if (StringUtils.isBlank(accessToken)) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        try {
            redisService.invalidateByAccessToken(accessToken);
        } catch (Exception e) {
            result = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        log.info("[退出登录成功],原token失效");
        return result;
    }
}
