package com.learn.designModel.Factory.AbstractFactory;

/**
 * autor:liman
 * comment:
 */
public class EnglishCourseFactory implements CourseFactory {
    @Override
    public ICourse createCourse() {
        return new EnglishCourse();
    }

    @Override
    public IPractise createPractise() {
        return new EnglishPractise();
    }

    @Override
    public IExam createExam() {
        return new EnglishExam();
    }
}
