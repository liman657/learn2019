package com.learn.designModel.ObserverPattern.MouseEvent;

/**
 * autor:liman
 * mobilNo:15528212893
 * mail:657271181@qq.com
 * comment:
 */
public interface MouseEventType {
    //单击
    public String ON_CLICK = "click";

    //双击
    public String ON_DOUBLE_CLICK = "doubleClick";

    //弹起
    public String ON_UP = "up";

    //按下
    public String ON_DOWN = "down";

    //移动
    public String ON_MOVE = "move";

    //滚动
    public  String ON_WHEEL = "wheel";

    //悬停
    public String ON_OVER = "over";

    //失焦
    public String ON_BLUR = "blur";

    //获焦
    public String ON_FOCUS = "focus";
}
