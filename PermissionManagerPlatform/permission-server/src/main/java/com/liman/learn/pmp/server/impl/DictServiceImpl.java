package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.QueryUtil;
import com.liman.learn.pmp.model.entity.SysDictEntity;
import com.liman.learn.pmp.model.mapper.SysDictDao;
import com.liman.learn.pmp.server.IDictService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/13
 * comment:
 */
@Service("dictService")
@Slf4j
public class DictServiceImpl extends ServiceImpl<SysDictDao, SysDictEntity> implements IDictService {

    @Override
    public PageUtil queryPage(Map<String, Object> params) {
        String name = (String)params.get("name");

        IPage queryPage=new QueryUtil<SysDictEntity>().getQueryPage(params);

        //查询包装器
        QueryWrapper<SysDictEntity> wrapper=new QueryWrapper<SysDictEntity>()
                .like(StringUtils.isNotBlank(name),"name", name);
        IPage<SysDictEntity> page=this.page(queryPage,wrapper);

        return new PageUtil(page);
    }
}
