package com.learn.springauthserver.service;

import com.learn.springauthmodel.entity.Log;
import com.learn.springauthmodel.entity.User;
import com.learn.springauthmodel.mapper.LogMapper;
import com.learn.springauthmodel.mapper.UserMapper;
import com.learn.springauthserver.dto.AccessTokenDto;
import com.learn.springauthserver.dto.UpdatePsdDto;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * autor:liman
 * createtime:2019/12/11
 * comment:增加日志记录
 */
@Service
@Slf4j
public class LogService {

    @Autowired
    private LogMapper logMapper;

    /**
     * 增加日志操作记录
     * @return
     */
    public int addLog(Log log){
        return logMapper.insert(log);
    }

}