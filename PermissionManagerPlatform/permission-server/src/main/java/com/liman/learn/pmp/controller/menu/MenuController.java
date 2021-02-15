package com.liman.learn.pmp.controller.menu;

import com.google.common.collect.Maps;
import com.liman.learn.common.response.BaseResponse;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.Constant;
import com.liman.learn.pmp.model.entity.SysMenuEntity;
import com.liman.learn.pmp.server.IMenuService;
import com.liman.learn.pmp.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/25
 * comment: 菜单controller
 */
@RestController
@Slf4j
@RequestMapping("/menu")
public class MenuController {

    @Autowired
    private IMenuService menuService;

    @RequestMapping("/list")
    @RequiresPermissions(value={"sys:menu:list"})
    public List<SysMenuEntity> list() {
        //第一种方式，借助mybatis-plus的查询方式实现
//        List<SysMenuEntity> menuList = menuService.subMenuList();
//        if (null != menuList && !menuList.isEmpty()) {//如果不为空，则需要通过子查询获取parentName
//            menuList.stream().forEach(entity -> {
//                if(Constant.MenuType.BUTTON.getValue() == entity.getType()) {//这里只针对按钮类型的资源查询父菜单名
//                    SysMenuEntity parentEntity = menuService.getById(entity.getParentId());
//                    entity.setParentName((parentEntity != null && StringUtils.isNotEmpty(parentEntity.getName()) ? parentEntity.getName() : ""));
//                }
//            });
//        }
//        return menuList;

        //第二种方式，自己写SQL
        return menuService.queryAll();
    }


    @RequestMapping("/select")
    @RequiresPermissions(value={"sys:menu:list"})
    public BaseResponse selectAllMenuInfo(){
        BaseResponse  response = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap = Maps.newHashMap();
        try{
            List<SysMenuEntity> rootMenuData = menuService.queryNotButtonList();

            SysMenuEntity root = new SysMenuEntity();
            root.setMenuId(Constant.TOP_MENU_ID);
            root.setName(Constant.TOP_MENU_NAME);
            root.setParentId(-1L);
            root.setOpen(true);

            rootMenuData.add(root);
            resMap.put("menuList",rootMenuData);
        }catch (Exception e){
            response = new BaseResponse(StatusCode.Fail);
        }
        response.setData(resMap);
        return response;
    }

    /**
     *
     * @param entity
     * @return
     */
    @RequestMapping(value="/save",method = RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions(value={"sys:menu:save"})
    public BaseResponse save(@RequestBody SysMenuEntity entity){
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("新增菜单~接收到数据：{}",entity);

            //校验添加菜单的合法性
            String result=this.validateForm(entity);
            if (StringUtils.isNotBlank(result)){
                return new BaseResponse(StatusCode.Fail.getCode(),result);
            }

            menuService.save(entity);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    @RequestMapping("/info/{menuId}")
    @RequiresPermissions(value={"sys:menu:list"})
    public BaseResponse getMenuInfo(@PathVariable Long menuId){
        if (menuId==null || menuId<=0){
            return new BaseResponse(StatusCode.InvalidParams);
        }

        BaseResponse response=new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap= Maps.newHashMap();
        try {
            resMap.put("menu",menuService.getById(menuId));
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }

        response.setData(resMap);

        return response;
    }

    /**
     * 修改菜单
     * @param menuEntity
     * @return
     */
    @RequestMapping(value="/update",method=RequestMethod.POST,consumes = MediaType.APPLICATION_JSON_VALUE)
    @RequiresPermissions(value={"sys:menu:update"})
    public BaseResponse updateMenuInfo(@RequestBody SysMenuEntity menuEntity){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        try{
            log.info("修改菜单~接收到的数据:{}",menuEntity);
            String result = this.validateForm(menuEntity);
            if(StringUtils.isNotBlank(result)){
                return new BaseResponse(StatusCode.Fail.getCode(),result);
            }
            menuService.updateById(menuEntity);
        }catch (Exception e){
            log.error("修改菜单数据出现异常，异常信息为：{}",e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        return response;
    }

    /**
     * 删除菜单数据
     * @param menuId
     * @return
     */
    @RequestMapping(value="/delete",method = RequestMethod.POST)
    @RequiresPermissions(value={"sys:menu:delete"})
    public BaseResponse deleteMenuInfo(Long menuId){
        if (menuId==null || menuId<=0 ){
            return new BaseResponse(StatusCode.InvalidParams);
        }
        BaseResponse response=new BaseResponse(StatusCode.Success);
        try {
            log.info("删除菜单~接收到数据：{}",menuId);

            SysMenuEntity entity=menuService.getById(menuId);
            if (entity==null){
                return new BaseResponse(StatusCode.MenuHasBeanDelete);
            }

            List<SysMenuEntity> list=menuService.queryByParentId(menuId);
            if (list!=null && !list.isEmpty()){
                return new BaseResponse(StatusCode.MenuHasSubMenuListCanNotDelete);
            }

            menuService.delete(menuId);
        }catch (Exception e){
            response=new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }

        return response;
    }

    /**
     * 获取用户的左侧菜单导航栏
     * @return
     */
    @RequestMapping(value="/nav")
    public BaseResponse getUserNavMenuList(){
        BaseResponse response = new BaseResponse(StatusCode.Success);
        Map<String,Object> resMap = Maps.newHashMap();
        Long currUserId = ShiroUtil.getUserId();
        try{
            //根据用户id获取用户指定的左侧菜单栏数据
            List<SysMenuEntity> userMenuList = menuService.getUserMenuList(currUserId);
            resMap.put("menuList",userMenuList);
            log.info("用户:{},获取的菜单数据为：{}",currUserId,userMenuList);
        }catch (Exception e){
            log.error("userId:{},获取用户左侧菜单导航栏出现异常，异常信息为:{}",currUserId,e);
            response = new BaseResponse(StatusCode.Fail.getCode(),e.getMessage());
        }
        response.setData(resMap);
        return response;
    }

    /**
     * 加入菜单时的数据校验
     * 1、菜单名和上级菜单id不能为空
     * 2、如果增加的是菜单，则必须输入菜单url
     * 3、
     * @param menu
     * @return
     */
    //验证参数是否正确
    private String validateForm(SysMenuEntity menu) {
        if (StringUtils.isBlank(menu.getName())) {
            return "菜单名称不能为空";
        }
        if (menu.getParentId() == null) {
            return "上级菜单不能为空";
        }

        //针对菜单组件的添加
        if (menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (StringUtils.isBlank(menu.getUrl())) {
                return "菜单链接url不能为空";
            }
        }

        //找到当前添加菜单的父级菜单，方便下一步的校验
        int parentType = Constant.MenuType.CATALOG.getValue();

        if (menu.getParentId() != 0) {
            SysMenuEntity parentMenu = menuService.getById(menu.getParentId());
            parentType = parentMenu.getType();
        }

        //如果是目录和菜单，则其父级只能是目录
        if (menu.getType() == Constant.MenuType.CATALOG.getValue() || menu.getType() == Constant.MenuType.MENU.getValue()) {
            if (parentType != Constant.MenuType.CATALOG.getValue()) {
                return "上级菜单只能为目录类型";
            }
            return "";
        }

        //如果当前类型是按钮，则其父级只能是菜单，不能是目录
        if (menu.getType() == Constant.MenuType.BUTTON.getValue()) {
            if (parentType != Constant.MenuType.MENU.getValue()) {
                return "上级菜单只能为菜单类型";
            }
            return "";
        }

        return "";
    }

}
