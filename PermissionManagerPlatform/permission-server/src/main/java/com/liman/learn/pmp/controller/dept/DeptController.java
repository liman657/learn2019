package com.liman.learn.pmp.controller.dept;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.Constant;
import com.liman.learn.common.utils.ValidatorUtil;
import com.liman.learn.pmp.model.entity.SysDeptEntity;
import com.liman.learn.pmp.server.IDeptService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.awt.*;
import java.util.List;
import java.util.Map;

import static com.liman.learn.pmp.util.ShiroUtil.getUserId;

/**
 * autor:liman
 * createtime:2021/1/23
 * comment: 部门信息Controller
 */
@RequestMapping("/dept")
@Slf4j
@RestController
public class DeptController {

    @Autowired
    private IDeptService deptService;

    @RequestMapping("/list")
//    @RequiresPermissions(value={"sys:dept:list"})
    public List<SysDeptEntity> list(){
        return deptService.queryAll(Maps.newHashMap());
    }

    //获取一级部门/顶级部门的deptId
    @RequestMapping("/info")
//    @RequiresPermissions(value={"sys:dept:list"})
    public BaseResponse getDeptInfo(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();
        Long deptId=0L; //这里只需要返回一级部门的id即可
        try {
            //数据视野决定的顶级部门id可能不是0
            if (getUserId() != Constant.SUPER_ADMIN){
                //涉及到数据视野的问题
            }
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        resMap.put("deptId",deptId);
        response.setData(resMap);

        return response;
    }

    /**
     * 获取部门列表，zTree控件专用
     * @return
     */
    @RequestMapping("/select")
//    @RequiresPermissions(value={"sys:dept:list"})
    public BaseResponse select(){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();

        List<SysDeptEntity> deptList= Lists.newLinkedList();
        try {
            deptList=deptService.queryAll(Maps.newHashMap());
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        resMap.put("deptList",deptList);
        response.setData(resMap);

        return response;
    }

    /**
     * 保存部门信息
     * @param entity
     * @param result
     * @return
     */
    @RequestMapping(value="/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions(value={"sys:dept:save"})
    public BaseResponse saveDeptInfo(@RequestBody @Validated SysDeptEntity entity, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.Fail.getCode(),res);
        }

        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("新增部门~接收到数据：{}",entity);

            deptService.save(entity);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 根据id删除部门信息
     * @param deptId
     * @return
     */
    @RequestMapping(value = "/delete")
    @RequiresPermissions(value={"sys:dept:delete"})
    public BaseResponse delete(Long deptId){
        if (deptId==null || deptId<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("删除部门~接收到数据：{}",deptId);

            //如果当前部门有子部门，则需要要求先删除下面的所有子部门，再删除当前部门
            List<Long> subIds=deptService.queryDeptByParentId(deptId);
            if (subIds!=null && !subIds.isEmpty()){
                //这里并没有操作子部门的删除，而是提示了一个错误信息
                return new BaseResponse(StatusCode.DeptHasSubDeptCanNotBeDelete);
            }

            deptService.removeById(deptId);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 获取部门详情
     * @param deptId
     * @return
     */
    @RequestMapping("/detail/{deptId}")
    @RequiresPermissions(value={"sys:dept:list"})
    public BaseResponse detail(@PathVariable Long deptId){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap=Maps.newHashMap();
        try {
            resMap.put("dept",deptService.getById(deptId));

        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        response.setData(resMap);

        return response;
    }

    /**
     * 修改部门信息
     * @param entity
     * @param result
     * @return
     */
    @RequestMapping(value = "/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @RequiresPermissions(value={"sys:dept:update"})
    public BaseResponse update(@RequestBody @Validated SysDeptEntity entity, BindingResult result){
        String res= ValidatorUtil.checkResult(result);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.Fail.getCode(),res);
        }
        if (entity.getDeptId()==null || entity.getDeptId()<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("修改部门~接收到数据：{}",entity);

            deptService.updateById(entity);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;

    }

}
