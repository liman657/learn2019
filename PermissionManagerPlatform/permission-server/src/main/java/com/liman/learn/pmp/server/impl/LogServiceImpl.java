package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.QueryUtil;
import com.liman.learn.pmp.model.entity.SysLogEntity;
import com.liman.learn.pmp.model.mapper.SysLogDao;
import com.liman.learn.pmp.server.ILogService;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/12
 * comment:
 */
@Service("logService")
public class LogServiceImpl extends ServiceImpl<SysLogDao,SysLogEntity> implements ILogService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String key = (String)params.get("key");

        IPage queryPage=new QueryUtil<SysLogEntity>().getQueryPage(params);

        QueryWrapper<SysLogEntity> wrapper=new QueryWrapper<SysLogEntity>()
                .like(StringUtils.isNotBlank(key),"username", key)
                .or(StringUtils.isNotBlank(key))
                .like(StringUtils.isNotBlank(key),"operation", key);
        IPage<SysLogEntity> page=this.page(queryPage,wrapper);

        return new PageUtil(page);
    }

    @Override
    public void truncate() {
        baseMapper.truncate();
    }
}
