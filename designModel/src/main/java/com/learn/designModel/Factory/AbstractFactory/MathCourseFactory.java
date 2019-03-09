package com.learn.designModel.Factory.AbstractFactory;

/**
 * autor:liman
 * comment:
 */
public class MathCourseFactory implements CourseFactory {
    @Override
    public ICourse createCourse() {
        return new MathCourse();
    }

    @Override
    public IPractise createPractise() {
        return new MathPractise();
    }

    @Override
    public IExam createExam() {
        return new MathExam();
    }
}
