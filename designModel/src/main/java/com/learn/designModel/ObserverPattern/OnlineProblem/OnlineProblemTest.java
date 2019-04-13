package com.learn.designModel.ObserverPattern.OnlineProblem;

/**
 * autor:liman
 * comment:
 */
public class OnlineProblemTest {

    public static void main(String[] args) throws NoSuchMethodException {
        ICoder coderLi = new CoderLi();
        ICoder coderWang = new CoderWang();
        ServerOnline serverOnline = new ServerOnline();
        serverOnline.addListener("ERRORLi",coderLi,
                coderLi.getClass().getMethod("problemMessage",new Class<?>[]{Message.class}));
        serverOnline.addListener("ERRORWang",coderWang,
                coderWang.getClass().getMethod("problemMessage",new Class<?>[]{Message.class}));

        serverOnline.runExceptionLi();
        serverOnline.runExceptionWang();

    }

}
