package com.liman.learn.pmp.controller.login;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.pmp.model.entity.SysUserEntity;
import com.liman.learn.pmp.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;

/**
 * autor:liman
 * createtime:2021/1/2
 * comment:
 */
@Controller
@Slf4j
public class LoginController {

    /**
     * kaptcha 为我们注入了一个producer，生成验证码的对象，我们直接调用即可
     */
    @Autowired
    private Producer producer;

    /**
     *
     * @param username
     * @param password
     * @param captcha
     * @return
     */
    @RequestMapping("/login")
    @ResponseBody
    public BaseResponse login(String username,String password,String captcha){
        log.info("{},开始进行登录，登录参数为,密码:{},验证码:{}",username,password,captcha);
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try{

            //校验验证码
//            String kaptcha = ShiroUtil.getKaptcha(Constants.KAPTCHA_SESSION_KEY);
//            if(!kaptcha.equals(captcha)){
//                log.warn("用户:{},输入的验证码为:{},正确的验证码为:{}",username,captcha,kaptcha);
//                return new BaseResponse(StatusCode.InvalidCode,"请输入正确的验证码");
//            }

            //UserRealm交给了SecurityManager管理，每次认证的时候，需要通过utils获取subject对象
            Subject subject = SecurityUtils.getSubject();
            if(!subject.isAuthenticated()){//如果没有得到授权，就需要进行登录。
                //登录的时候，需要根据用户名和密码生成UsernamePasswordToken，并交给subject进行login操作。
                UsernamePasswordToken usernamePasswordToken = new UsernamePasswordToken(username,password);
                subject.login(usernamePasswordToken);
            }


        }catch (Exception e){
            log.error("登录出现异常，异常信息为:{}",e);
            return new BaseResponse(StatusCode.Fail,e.getMessage());
        }
        log.info("用户:{},登录成功!",username);
        return result;
    }

    @RequestMapping("/logout")
    public String logOut(){
        //销毁当前shiro的会话
        SysUserEntity userEntity = ShiroUtil.getUserEntity();
        String username = userEntity.getUsername();
        log.info("用户:{},退出",username);
        ShiroUtil.logout();
        return "redirect:login.html";
    }

    /**
     * 生成验证码
     */
    @RequestMapping("/captcha.jpg")
    public void getKaptcha(HttpServletResponse response) throws Exception{
        response.setHeader("Cache-Control", "no-store, no-cache");
        response.setContentType("image/jpeg");

        //生成文字验证码
        String text = producer.createText();
        //生成图片验证码
        BufferedImage image = producer.createImage(text);
        //保存到shiro session
        ShiroUtil.setSessionAttribute(Constants.KAPTCHA_SESSION_KEY, text);

        ServletOutputStream out = response.getOutputStream();
        ImageIO.write(image, "jpg", out);

        log.info("生成的验证码为:{}",text);
    }

}
