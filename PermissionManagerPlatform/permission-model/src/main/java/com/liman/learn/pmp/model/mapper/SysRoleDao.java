package com.liman.learn.pmp.model.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.liman.learn.pmp.model.entity.SysRoleEntity;
import org.apache.ibatis.annotations.Mapper;

//角色管理
@Mapper
public interface SysRoleDao extends BaseMapper<SysRoleEntity> {
	

}
