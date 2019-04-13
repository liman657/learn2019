package com.learn.designModel.ObserverPattern.JDKObserver;

/**
 * autor:liman
 * comment:
 */
public class JDKObserverTest {

    public static void main(String[] args) {
        Teacher teacher = new Teacher("liman");

        Forum forum = Forum.getForum();

        Question question = new Question();
        question.setContent("你最喜欢的人是谁？");
        question.setUserName("test");

        forum.addObserver(teacher);
        forum.publicQuestion(question);
    }

}
