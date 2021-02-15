package com.liman.learn.pmp.controller.user;

import com.google.common.collect.Maps;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.Constant;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.ValidatorUtil;
import com.liman.learn.pmp.annotation.LogOperatorAnnotation;
import com.liman.learn.pmp.model.entity.SysUserEntity;
import com.liman.learn.pmp.server.IUserManagerService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import java.util.Map;
import static com.liman.learn.pmp.util.ShiroUtil.getUserId;

/**
 * autor:liman
 * createtime:2021/2/1
 * comment:用户信息管理controller
 */
@Controller
@RequestMapping("/userManager")
@Slf4j
@ResponseBody
public class UserInfoManagerController {

    @Autowired
    private IUserManagerService userManagerService;

    @RequestMapping("/list")
    @RequiresPermissions(value={"sys:user:list"})
    public BaseResponse getAllUserInfoList(@RequestParam Map<String, Object> paramMap) {
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String, Object> resMap = Maps.newHashMap();
        try {
            log.info("用户模块~分页列表模糊查询：{}", paramMap);

            PageUtil page = userManagerService.queryPage(paramMap);
            resMap.put("page", page);

        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        response.setData(resMap);
        return response;
    }

    /**
     * 新增员工信息
     *
     * @param userEntity
     * @param result
     * @return
     */
    @RequestMapping(value = "/save")
    @RequiresPermissions(value={"sys:user:save"})
    @LogOperatorAnnotation("新增用户信息")
    public BaseResponse saveUserInfo(@RequestBody @Validated SysUserEntity userEntity, BindingResult result) {
        String res = ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)) {
            return new BaseResponse(StatusCode.InvalidParams.getCode(), res);
        }
        if (StringUtils.isBlank(userEntity.getPassword())) {
            return new BaseResponse(StatusCode.PasswordCanNotBlank);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            userManagerService.saveUser(userEntity);
        } catch (Exception e) {
            log.error("新增用户出现异常，异常信息为:{}", e);
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping(value = "/delete", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions(value={"sys:user:delete"})
    @LogOperatorAnnotation("删除用户信息")
    public BaseResponse deleteUserInfo(@RequestBody Long[] userIds) {
        if (userIds == null || userIds.length <= 0) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        //超级管理员~admin不能删除；当前登录用户不能删
        //if (Arrays.asList(ids).contains(Constant.SUPER_ADMIN)){
        if (ArrayUtils.contains(userIds, Constant.SUPER_ADMIN)) {
            return new BaseResponse(StatusCode.SysUserCanNotBeDelete);
        }
        if (ArrayUtils.contains(userIds, getUserId())) {
            return new BaseResponse(StatusCode.CurrUserCanNotBeDelete);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("删除用户~接收到数据：{}", userIds);

            userManagerService.deleteUserBatch(userIds);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    /**
     * 修改用户信息
     *
     * @param userEntity
     * @param bindingResult
     * @return
     */
    @RequestMapping(value = "/update", method = RequestMethod.POST, consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions(value={"sys:user:update"})
    @LogOperatorAnnotation("修改用户信息")
    public BaseResponse updateUserInfo(@RequestBody @Validated SysUserEntity userEntity, BindingResult bindingResult) {
        String res= ValidatorUtil.checkResult(bindingResult);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("用户模块~修改用户：{}",userEntity);

            userManagerService.updateUser(userEntity);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value="/info/{userId}")
    @RequiresPermissions(value={"sys:user:list"})
    public BaseResponse getUserInfo(@PathVariable Long userId){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();
        try {
            log.info("用户模块~获取详情：{}",userId);

            resMap.put("user",userManagerService.getUserInfo(userId));
            response.setData(resMap);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return response;
    }

}
