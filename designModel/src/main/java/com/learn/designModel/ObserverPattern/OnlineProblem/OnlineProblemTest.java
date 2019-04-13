package com.learn.designModel.ObserverPattern.OnlineProblem;

/**
 * autor:liman
 * comment:
 */
public class OnlineProblemTest {

    public static void main(String[] args) throws NoSuchMethodException {
        CoderLi coderLi = new CoderLi();
        ServerOnline serverOnline = new ServerOnline();
        serverOnline.addListener("ERROR",coderLi,
                coderLi.getClass().getMethod("problemMessage",new Class<?>[]{Message.class}));

        serverOnline.runException();
    }

}
