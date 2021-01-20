package com.liman.learn.pmp.server.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.base.Joiner;
import com.liman.learn.common.response.StatusCode;
import com.liman.learn.common.utils.CommonUtil;
import com.liman.learn.common.utils.PageUtil;
import com.liman.learn.common.utils.QueryUtil;
import com.liman.learn.pmp.model.entity.SysPostEntity;
import com.liman.learn.pmp.model.mapper.SysPostDao;
import com.liman.learn.pmp.server.IPostService;
import org.apache.commons.lang.StringUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * autor:liman
 * createtime:2021/1/5
 * comment:
 */
@Service("postService")
public class PostServiceImpl extends ServiceImpl<SysPostDao, SysPostEntity> implements IPostService {
    @Override
    public PageUtil queryPage(Map<String, Object> params) {

        String search = (params.get("search") == null ? "" : params.get("search").toString());

        IPage<SysPostEntity> queryPage =
                new QueryUtil<SysPostEntity>().getQueryPage(params);

        QueryWrapper<SysPostEntity> wrapper = new QueryWrapper<SysPostEntity>()
                .like(StringUtils.isNotBlank(search), "post_code", search.trim())
                .or(StringUtils.isNotBlank(search))
                .like(StringUtils.isNotBlank(search), "post_name", search.trim());

        IPage<SysPostEntity> pageResult = this.page(queryPage, wrapper);
        return new PageUtil(pageResult);
    }

    @Override
    public void savePost(SysPostEntity entity) {
        SysPostEntity originalEntity = this.getOne(new QueryWrapper<SysPostEntity>().eq("post_code", entity.getPostCode()));
        if (null != originalEntity) {
            throw new RuntimeException(StatusCode.PostCodeHasExist.getMsg());
        }
        entity.setCreateTime(DateTime.now().toDate());
        save(entity);
    }

    @Override
    public void updatePost(SysPostEntity entity) {
        SysPostEntity old = this.getById(entity.getPostId());
        if (old != null && !old.getPostCode().equals(entity.getPostCode())) {
            SysPostEntity postCodeEntity = this.getOne(new QueryWrapper<SysPostEntity>().eq("post_code", entity.getPostCode()));
            if (postCodeEntity != null) {
                throw new RuntimeException(StatusCode.PostCodeHasExist.getMsg());
            }
        }

        entity.setUpdateTime(DateTime.now().toDate());
        updateById(entity);
    }

    /**
     * 批量删除
     *
     * @param ids
     */
    @Override
    public void deletePatch(Long[] ids) {
        String todeleteIds = Joiner.on(",").join(ids);
        baseMapper.deleteBatch(CommonUtil.concatStrToInt(todeleteIds, ","));
    }
}
