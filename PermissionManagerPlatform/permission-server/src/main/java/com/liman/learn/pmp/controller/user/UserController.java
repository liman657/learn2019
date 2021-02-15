package com.liman.learn.pmp.controller.user;

import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.pmp.annotation.LogOperatorAnnotation;
import com.liman.learn.pmp.controller.BaseController;
import com.liman.learn.pmp.model.entity.SysUserEntity;
import com.liman.learn.pmp.server.IUserService;
import com.liman.learn.pmp.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * autor:liman
 * createtime:2021/1/4
 * comment:
 */
@Controller
@RequestMapping("/user")
@Slf4j
@ResponseBody
public class UserController extends BaseController {

    @Autowired
    private IUserService userService;

    /**
     * 获取当前登录用户
     * @return
     */
    @RequestMapping(value = "/info",method = RequestMethod.GET)
    public BaseResponse getCurrUserInfo(){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        try {
            SysUserEntity userEntity= getCurUserInfo();
            result.setData(userEntity);
        }catch (Exception e){
            log.error("获取当前用户信息异常，异常信息为:{}",e);

        }
        return result;
    }

    /**
     * 用户密码修改
     * @param oldPassword
     * @param newPassword
     * @return
     */
    @RequestMapping("/updatePassword")
    @RequiresPermissions("sys:user:resetPsd")
    @LogOperatorAnnotation("重置管理员密码")
    public BaseResponse updateUserPassword(String oldPassword,String newPassword){
        BaseResponse result = new BaseResponse(StatusCode.Success);
        if(StringUtils.isBlank(oldPassword) || StringUtils.isBlank(newPassword)){
            result = new BaseResponse(StatusCode.PasswordCanNotBlank);
            return result;
        }

        try{
            //获取当前的用户信息
            SysUserEntity curUserInfo = getCurUserInfo();
            //获取用户盐值
            final String saltValue = curUserInfo.getSalt();
            String oldPsdInDb = curUserInfo.getPassword();
            String frontEncrpyPsd = ShiroUtil.sha256(oldPassword,saltValue);
            if(!oldPsdInDb.equals(frontEncrpyPsd)){
                return new BaseResponse(StatusCode.OldPasswordNotMatch);
            }
            String toUpdatePsd = ShiroUtil.sha256(newPassword,saltValue);
            userService.updatePassword(curUserInfo.getUserId(),oldPsdInDb,toUpdatePsd);
        }catch (Exception e){
            result = new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return result;
    }

}
