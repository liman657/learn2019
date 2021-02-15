package com.liman.learn.pmp.controller.dict;

import com.google.common.collect.Maps;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.ValidatorUtil;
import com.liman.learn.pmp.model.entity.SysDictEntity;
import com.liman.learn.pmp.server.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/13
 * comment: 字典管理
 */
@RestController
@Slf4j
@RequestMapping("/dict")
public class DictController {

    @Autowired
    private IDictService dictService;

    //列表
    @RequestMapping("/list")
    @RequiresPermissions("sys:dict:list")
    public BaseResponse list(@RequestParam Map<String, Object> params){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            PageUtil page = dictService.queryPage(params);

            Map<String,Object> resMap= Maps.newHashMap();
            resMap.put("page", page);

            response.setData(resMap);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }


    //详情
    @RequestMapping("/info/{id}")
    @RequiresPermissions("sys:dict:info")
    public BaseResponse info(@PathVariable Long id){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();
        try {
            SysDictEntity entity = dictService.getById(id);

            resMap.put("dict", entity);
            response.setData(resMap);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.UpdatePasswordFail);
        }
        return response;
    }

    //新增
    @RequestMapping("/save")
    @RequiresPermissions("sys:dict:save")
    public BaseResponse save(@RequestBody @Validated SysDictEntity dict, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.Fail.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            dictService.save(dict);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //修改
    @RequestMapping("/update")
    @RequiresPermissions("sys:dict:update")
    public BaseResponse update(@RequestBody @Validated SysDictEntity dict, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.Fail.getCode(),res);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            dictService.updateById(dict);

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    //删除
    @RequestMapping("/delete")
    @RequiresPermissions("sys:dict:delete")
    public BaseResponse delete(@RequestBody Long[] ids){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try {
            dictService.removeByIds(Arrays.asList(ids));
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }
}
