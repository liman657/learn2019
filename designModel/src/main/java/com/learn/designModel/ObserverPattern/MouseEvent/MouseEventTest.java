package com.learn.designModel.ObserverPattern.MouseEvent;

/**
 * autor:liman
 * comment:
 */
public class MouseEventTest {
    public static void main(String[] args) {

        MouseCallBack callback = new MouseCallBack();

        Mouse mouse = new Mouse();

        //@谁？  @回调方法
        mouse.addListener(MouseEventType.ON_CLICK,callback);
        mouse.addListener(MouseEventType.ON_FOCUS,callback);

        mouse.click();

        mouse.focus();
    }
}
