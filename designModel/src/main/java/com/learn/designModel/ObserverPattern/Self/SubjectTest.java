package com.learn.designModel.ObserverPattern.Self;

import java.lang.reflect.Method;
import java.util.Observable;

/**
 * autor:liman
 * comment:
 */
public class SubjectTest {

    public static void main(String[] args) throws NoSuchMethodException {
        Subject subject = new Subject();
        Observer observer = new Observer();
        Method advice = observer.getClass().getMethod("advice",new Class<?>[]{Event.class});

        subject.addListener("ON_ADD",observer,advice);

        subject.add();

    }

}
