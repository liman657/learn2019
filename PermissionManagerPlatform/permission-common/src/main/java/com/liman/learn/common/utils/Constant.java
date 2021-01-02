package com.liman.learn.common.utils;

import com.google.common.collect.Sets;

import java.text.SimpleDateFormat;
import java.util.Set;

/**
 * 常量
 * @Author:debug (SteadyJack)
 * @Date: 2019/8/1 22:23
 */
public class Constant {

    public static final SimpleDateFormat DATE_FORMAT=new SimpleDateFormat("yyyy-MM-dd");

    public static final SimpleDateFormat DATE_TIME_FORMAT=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

    //超级管理员Id
	public static final Long SUPER_ADMIN = 1L;

	//最顶级的部门Id
	public static final long TOP_DEPT_ID = 0;

    //最顶级的菜单Id
    public static final long TOP_MENU_ID = 0;

	//最顶级的部门名称
	public static final String TOP_DEPT_NAME = "一级部门";

    //最顶级的菜单名称
    public static final String TOP_MENU_NAME = "一级菜单";

	//数据权限过滤
	public static final String SQL_FILTER = "sql_filter";

	//当前页码
    public static final String PAGE = "page";

    //每页显示记录数
    public static final String LIMIT = "limit";

    //排序字段
    public static final String ORDER_FIELD = "sidx";

    //排序方式
    public static final String ORDER = "order";

    //升序
    public static final String ASC = "asc";

    //降序
    public static final String DESC = "desc";

    //指定字段
    public static final String FieldUserId="user_id";

    public static final String FieldCreateTime="create_time";

    //指定的菜单Id不能删除
    public static final Set<Long> MenuIds= Sets.newHashSet(1L,2L,3L,4L,29L,31L);

    public static final String Profiles="dev";

    public static final String DefaultPassword="pmp123456";


	/**
	 * 菜单类型
	 */
    public enum MenuType {
        //目录
    	CATALOG(0),
        //菜单
        MENU(1),
        //按钮
        BUTTON(2);

        private int value;

        MenuType(int value) {
            this.value = value;
        }

        public int getValue() {
            return value;
        }
    }

    /**
     * 打卡状态
     */
    public enum AttendStatus{

        Yes(1,"已打卡"),
        No(0,"未打卡");

        private int code;
        private String msg;

        public static AttendStatus byCode(final int code){
            AttendStatus res=null;
            switch (code){
                case 1:
                    res=Yes;
                    break;
                case 0:
                    res=No;
                    break;
            }
            return res;
        }

        AttendStatus(int code, String msg) {
            this.code = code;
            this.msg = msg;
        }

        public int getCode() {
            return code;
        }

        public void setCode(int code) {
            this.code = code;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }
    }
}















