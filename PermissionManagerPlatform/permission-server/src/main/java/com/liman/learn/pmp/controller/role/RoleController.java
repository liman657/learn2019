package com.liman.learn.pmp.controller.role;

import com.google.common.collect.Maps;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.ValidatorUtil;
import com.liman.learn.pmp.model.entity.SysRoleEntity;
import com.liman.learn.pmp.server.IRoleDeptService;
import com.liman.learn.pmp.server.IRoleMenuService;
import com.liman.learn.pmp.server.IRoleService;
import lombok.extern.slf4j.Slf4j;
import net.bytebuddy.asm.Advice;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/31
 * comment:角色控制器
 */
@RestController
@Slf4j
@RequestMapping("role")
public class RoleController {

    @Autowired
    private IRoleService roleService;
    @Autowired
    private IRoleMenuService roleMenuService;
    @Autowired
    private IRoleDeptService roleDeptService;

    @RequestMapping(value="/list")
    public BaseResponse getRoleList(@RequestParam Map<String,Object> paramMap){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            Map<String,Object> resMap= Maps.newHashMap();

            PageUtil page=roleService.queryPage(paramMap);
            resMap.put("page",page);

            response.setData(resMap);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping(value="/save",method = RequestMethod.POST,consumes=MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse saveRoleInfo(@RequestBody @Validated SysRoleEntity roleEntity, BindingResult validateResult){
        String res = ValidatorUtil.checkResult(validateResult);
        if(StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams,res);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            log.info("开始新增角色信息，参数为:{}",roleEntity);

            //保存角色信息的同时，需要保存角色与菜单的关联信息，角色与部门的关联信息
            roleService.saveRole(roleEntity);
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     *
     * @param id
     * @return
     */
    @RequestMapping(value="/info/{id}")
    public BaseResponse getRoleInfo(@PathVariable Long id){
        if (id == null || id <= 0) {
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String, Object> resMap = Maps.newHashMap();
        try {
            SysRoleEntity role=roleService.getById(id);

            //获取角色对应的菜单列表
            List<Long> menuIdList=roleMenuService.queryMenuByRoleIdList(id);
            role.setMenuIdList(menuIdList);

            //获取角色对应的部门列表
            List<Long> deptIdList=roleDeptService.queryDeptByRoleIdList(id);
            role.setDeptIdList(deptIdList);

            resMap.put("role",role);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        response.setData(resMap);
        return response;
    }

    /**
     * 修改角色controller
     * @param roleEntity
     * @param bindingResult
     * @return
     */
    @RequestMapping(value="/update",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse updateRoleInfo(@RequestBody @Validated SysRoleEntity roleEntity,BindingResult bindingResult){
        String res=ValidatorUtil.checkResult(bindingResult);
        if (StringUtils.isNotBlank(res)){
            return new BaseResponse(StatusCode.InvalidParams.getCode(),res);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("修改角色~接收到数据：{}",roleEntity);

            roleService.updateRole(roleEntity);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

    @RequestMapping(value="/delete",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    public BaseResponse deleteRoleInfo(@RequestBody Long[] roleIds){
        if (roleIds==null || roleIds.length<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try {
            log.info("删除角色~接收到数据：{}",roleIds);

            roleService.deleteBatch(roleIds);
        } catch (Exception e) {
            response = new BaseResponse(StatusCode.Fail.getCode(), e.getMessage());
        }
        return response;
    }

}
