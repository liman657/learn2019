package com.liman.learn.common.utils;/**
 * Created by Administrator on 2019/8/1.
 */

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.Data;
import lombok.ToString;

import java.io.Serializable;
import java.util.List;

//统一封装的分页工具类 ：也可以指定 long类型；但是一般int就够用了 ~ 根据具体的业务情况而定!
@Data
@ToString
public class PageUtil implements Serializable {

    private static final long serialVersionUID = 1L;

    //总记录数
    private int totalCount;

    //每页记录数
    private int pageSize;

    //总页数
    private int totalPage;

    //当前第几页
    private int currPage;

    //列表数据
    private List<?> list;

    /**
     * 分页
     * @param list        列表数据
     * @param totalCount  总记录数
     * @param pageSize    每页记录数
     * @param currPage    当前页数
     */
    public PageUtil(List<?> list, int totalCount, int pageSize, int currPage) {
        this.list = list;
        this.totalCount = totalCount;
        this.pageSize = pageSize;
        this.currPage = currPage;
        this.totalPage = (int)Math.ceil((double)totalCount/pageSize);
    }

    /**
     * 分页
     */
    public PageUtil(IPage<?> page) {
        this.list = page.getRecords();
        this.totalCount = (int)page.getTotal();
        this.pageSize = (int)page.getSize();
        this.currPage = (int)page.getCurrent();
        this.totalPage = (int)page.getPages();
    }
}
