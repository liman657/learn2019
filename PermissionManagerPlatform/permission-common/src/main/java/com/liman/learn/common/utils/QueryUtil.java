package com.liman.learn.common.utils;/**
 * Created by Administrator on 2019/7/18.
 */


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.liman.learn.common.filter.SQLFilter;
import org.apache.commons.lang.StringUtils;
import org.apache.tomcat.util.bcel.Const;

import java.util.Map;

/**
 * 查询统一封装的工具类
 *
 * @Author:debug (SteadyJack)
 * @Date: 2019/8/1 22:23
 **/
public class QueryUtil<T> {

    /**
     * 对前端传递过来的参数进行转换
     *
     * @param params
     * @return
     */
    public IPage<T> getPage(Map<String, Object> params) {
        return this.getPage(params, null, false);
    }

    /**
     * @param params            前端传递过来的参数
     * @param defaultOrderField 默认排序的字段
     * @param isAsc             升序与否
     * @return
     */
    public IPage<T> getPage(Map<String, Object> params, String defaultOrderField, boolean isAsc) {
        long curPage = 1;
        long limit = 10;
        if (params.get(Constant.PAGE) != null) {
            curPage = Long.parseLong((String) params.get(Constant.PAGE));
        }

        if (params.get(Constant.LIMIT) != null) {
            limit = Long.parseLong((String) params.get(Constant.LIMIT));
        }

        //分页对象
        Page<T> page = new Page<>(curPage, limit);

        //分页参数
        params.put(Constant.PAGE, page);

        String orderField = SQLFilter.sqlInject((String) params.get(Constant.ORDER_FIELD));
        String order = (String) params.get(Constant.ORDER);
        if(StringUtils.isNotEmpty(orderField) && StringUtils.isNotEmpty(order)){
            if(Constant.ASC.equalsIgnoreCase(order)){
                return page.setAsc(orderField);
            }else{
                return page.setDesc(orderField);
            }
        }

        if(isAsc){
            page.setAsc(defaultOrderField);
        }else{
            page.setDesc(defaultOrderField);
        }
        return page;
    }

    /**
     * 重载的查询方法
     * @param params
     * @return
     */
    public IPage<T> getQueryPage(Map<String,Object> params){
        long curPage = 1;
        long limit = 10;

        if(params.get(Constant.PAGE)!=null){
            curPage = Long.valueOf(params.get(Constant.PAGE).toString());
        }

        if(params.get(Constant.LIMIT) != null){
            limit = Long.valueOf(params.get(Constant.LIMIT).toString());
        }

        //分页对象
        Page<T> page = new Page<>(curPage,limit);

        if(params.get(Constant.ORDER) != null && params.get(Constant.ORDER_FIELD)!=null){
            SQLFilter.sqlInject((String) params.get(Constant.ORDER_FIELD));

            if(Constant.ASC.equalsIgnoreCase(params.get(Constant.ORDER).toString())) {
                return page.setAsc(params.get(Constant.ORDER_FIELD).toString());
            }else {
                return page.setDesc(params.get(Constant.ORDER_FIELD).toString());
            }
        }

        return page;
    }


}




























