package com.learn.springauthserver.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.learn.springauthmodel.entity.User;
import com.learn.springauthserver.contants.Constant;
import com.learn.springauthserver.dto.UpdatePsdDto;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpSession;

/**
 * autor:liman
 * createtime:2019/12/22
 * comment:
 */
@Service
@Slf4j
public class SessionAuthService {

    @Autowired
    private UserService userService;

    @Autowired
    private ObjectMapper objectMapper;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    private CommonService commonService;

    /**
     * 登录认证，session，这里创建session的方式有点奇怪
     *
     * @param userName
     * @param password
     * @param session
     * @return
     */
    public User authUser(String userName, String password,
                         HttpSession session) {
        User user = userService.authUser(userName, password);
        if (user != null) {
            user.setPassword(null);
            //将user信息存入session
            //TODO:这居然就是创建session？
            session.setAttribute(session.getId(), user);

            //TODO:还需要为当前成功建立的session设置过期时间
            //TODO:超时时间，指的是不进行任何操作的情况下的超时时间
            session.setMaxInactiveInterval(Constant.SESSION_EXPIRE);
            return user;
        }
        return null;
    }

    /**
     * 修改密码
     *
     * @param updatePsdDto
     * @param session
     */
    public void updatePassword(final UpdatePsdDto updatePsdDto, HttpSession session) {
        if (session != null && session.getAttribute(session.getId()) != null) {
            //从session中获取用户信息
            User currUser = (User) session.getAttribute(session.getId());

            User user = userService.selectUserByUserName(currUser.getUserName());
            if (user == null) {
                throw new RuntimeException("当前用户不存在，修改密码失败");
            }
            if (!user.getPassword().equals(updatePsdDto.getOldPassword())) {
                throw new RuntimeException("旧密码不匹配");
            }

            int res = userService.updatePassword(currUser.getUserName(), updatePsdDto.getOldPassword()
                    , updatePsdDto.getNewPassword());
            if (res <= 0) {
                throw new RuntimeException("修改密码失败");
            }
            //TODO:修改了密码之后，需要失效session
        }
    }

    public void invalidateSession(HttpSession session) {
        String sessionId = session.getId();
        if (session.getAttribute(sessionId) != null) {
            //TODO:session认证方式，让其失效就是删除sessionId对应的对象
            session.removeAttribute(sessionId);
        }
    }
}
