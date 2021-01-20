package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.pmp.model.entity.SysPostEntity;

import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/5
 * comment:
 */
public interface IPostService extends IService<SysPostEntity> {

    PageUtil queryPage(Map<String,Object> params);

    void savePost(SysPostEntity entity);

    void updatePost(SysPostEntity entity);

    void deletePatch(Long[] ids);

}
