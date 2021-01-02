package com.liman.learn.pmp.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liman.learn.pmp.model.entity.SysRoleDeptEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

//角色与部门关联关系
@Mapper
public interface SysRoleDeptDao extends BaseMapper<SysRoleDeptEntity> {
	
	//根据角色Id，获取部门Id列表
	List<Long> queryDeptIdList(Long[] roleIds);

	//根据角色Id数组，批量删除
	int deleteBatch(@Param("roleIds") String roleIds);

	List<Long> queryDeptIdListByRoleId(@Param("roleId") Long roleId);
}
