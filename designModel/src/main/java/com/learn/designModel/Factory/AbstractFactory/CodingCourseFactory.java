package com.learn.designModel.Factory.AbstractFactory;

/**
 * autor:liman
 * comment:
 */
public class CodingCourseFactory implements CourseFactory {

    @Override
    public ICourse createCourse() {
        return new CodingCourse();
    }

    @Override
    public IPractise createPractise() {
        return new CodingPractise();
    }

    @Override
    public IExam createExam() {
//        return new CodingExam();
        return null;
    }
}
