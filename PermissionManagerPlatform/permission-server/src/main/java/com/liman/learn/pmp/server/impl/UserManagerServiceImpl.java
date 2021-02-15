package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liman.learn.common.utils.Constant;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.QueryUtil;
import com.liman.learn.pmp.model.entity.SysDeptEntity;
import com.liman.learn.pmp.model.entity.SysUserEntity;
import com.liman.learn.pmp.model.entity.SysUserPostEntity;
import com.liman.learn.pmp.model.entity.SysUserRoleEntity;
import com.liman.learn.pmp.model.mapper.SysUserDao;
import com.liman.learn.pmp.server.IDeptService;
import com.liman.learn.pmp.server.IUserManagerService;
import com.liman.learn.pmp.server.IUserPostService;
import com.liman.learn.pmp.server.IUserRoleService;
import com.liman.learn.pmp.util.ShiroUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/1
 * comment:
 */
@Service("userManagerService")
@Slf4j
public class UserManagerServiceImpl extends ServiceImpl<SysUserDao,SysUserEntity> implements IUserManagerService {

    @Autowired
    private IDeptService deptService;
    @Autowired
    private IUserPostService userPostService;
    @Autowired
    private IUserRoleService userRoleService;

    //列表分页模糊查询
    @Override
    public PageUtil queryPage(Map<String, Object> map) {
        String search=(map.get("username")!=null)? (String) map.get("username") :"";

        IPage<SysUserEntity> iPage=new QueryUtil<SysUserEntity>().getQueryPage(map);

        QueryWrapper wrapper=new QueryWrapper<SysUserEntity>()
                .like(StringUtils.isNotBlank(search),"username",search.trim())
                .or(StringUtils.isNotBlank(search.trim()))
                .like(StringUtils.isNotBlank(search),"name",search.trim());
        IPage<SysUserEntity> resPage=this.page(iPage,wrapper);

        //获取用户所属的部门、用户的岗位信息
        SysDeptEntity dept;
        for (SysUserEntity user:resPage.getRecords()){
            try {
                dept=deptService.getById(user.getDeptId());
                user.setDeptName((dept!=null && StringUtils.isNotBlank(dept.getName()))? dept.getName() : "");

                String postName=userPostService.getPostNameByUserId(user.getUserId());
                user.setPostName(postName);
            }catch (Exception e){
                e.printStackTrace();
            }
        }

        return new PageUtil(resPage);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void saveUser(SysUserEntity entity) {
        if (this.getOne(new QueryWrapper<SysUserEntity>().eq("username",entity.getUsername())) !=null ){
            throw new RuntimeException("用户名已存在!");
        }

        entity.setCreateTime(new Date());

        //加密密码串
        String salt=RandomStringUtils.randomAlphanumeric(20);
        String password=ShiroUtil.sha256(entity.getPassword(),salt);
        entity.setPassword(password);
        entity.setSalt(salt);
        this.save(entity);

        //维护好用户~角色的关联关系
        userRoleService.saveOrUpdate(entity.getUserId(),entity.getRoleIdList());
        //维护好用户~岗位的关联关系
        userPostService.saveOrUpdate(entity.getUserId(),entity.getPostIdList());
    }

    /**
     * 批量删除用户信息
     * @param ids
     */
    @Override
    public void deleteUserBatch(Long[] ids) {
        if (ids!=null && ids.length>0){
            List<Long> userIds=Arrays.asList(ids);

            this.removeByIds(userIds);

            /*for (Long uId:userIds){
                sysUserRoleService.remove(new QueryWrapper<SysUserRoleEntity>().eq("user_id",uId));
                sysUserPostService.remove(new QueryWrapper<SysUserPostEntity>().eq("user_id",uId));
            }*/

            //java8的写法
            userIds.stream().forEach(uId -> userRoleService.remove(new QueryWrapper<SysUserRoleEntity>().eq("user_id",uId)));
            userIds.stream().forEach(uId -> userPostService.remove(new QueryWrapper<SysUserPostEntity>().eq("user_id",uId)));
        }
    }

    @Override
    public void updateUser(SysUserEntity entity) {
        SysUserEntity old=this.getById(entity.getUserId());
        if (old==null){
            log.info("该用户已经被删除");
            return;
        }
        if (!old.getUsername().equals(entity.getUsername())){
            if (this.getOne(new QueryWrapper<SysUserEntity>().eq("username",entity.getUsername())) !=null ){
                throw new RuntimeException("修改后的用户名已存在!");
            }
        }

        //判断密码是否为空
        if (StringUtils.isNotBlank(entity.getPassword())) {
            String password = ShiroUtil.sha256(entity.getPassword(), old.getSalt());
            entity.setPassword(password);
        }
        this.updateById(entity);

        //维护好用户~角色的关联关系
        userRoleService.saveOrUpdate(entity.getUserId(),entity.getRoleIdList());
        //维护好用户~岗位的关联关系
        userPostService.saveOrUpdate(entity.getUserId(),entity.getPostIdList());
    }

    //获取用户详情~包括其分配的角色、岗位关联信息
    @Override
    public SysUserEntity getUserInfo(Long userId) {
        SysUserEntity entity=this.getById(userId);

        //获取用户分配的角色关联信息
        List<Long> roleIds=userRoleService.queryRoleIdList(userId);
        entity.setRoleIdList(roleIds);

        //获取用户分配的岗位关联信息
        List<Long> postIds=userPostService.queryPostIdList(userId);
        entity.setPostIdList(postIds);

        return entity;
    }


    /**
     * 批量重置员工密码
     * @param ids
     */
    public void resetPassword(Long[] ids){
        if (ids!=null && ids.length>0){
            SysUserEntity entity;
            for (Long uId:ids){
                entity=new SysUserEntity();

                String salt=RandomStringUtils.randomAlphanumeric(20);
                String newPsd=ShiroUtil.sha256(Constant.DefaultPassword,salt);
                entity.setPassword(newPsd);
                entity.setSalt(salt);

                this.update(entity,new QueryWrapper<SysUserEntity>().eq("user_id",uId));
            }
        }
    }
}
