package com.liman.learn.pmp.server;

import com.baomidou.mybatisplus.extension.service.IService;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.pmp.model.entity.SysDictEntity;

import javax.jnlp.IntegrationService;
import java.util.Map;

/**
 * autor:liman
 * createtime:2021/2/13
 * comment:
 */
public interface IDictService extends IService<SysDictEntity> {

    PageUtil queryPage(Map<String, Object> params);

}
