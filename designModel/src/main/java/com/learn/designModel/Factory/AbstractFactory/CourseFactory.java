package com.learn.designModel.Factory.AbstractFactory;

/**
 * autor:liman
 * comment:
 */
public interface CourseFactory {

    public ICourse createCourse();

    public IPractise createPractise();

    public IExam createExam();

}
