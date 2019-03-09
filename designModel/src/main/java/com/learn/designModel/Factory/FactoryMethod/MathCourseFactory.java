package com.learn.designModel.Factory.FactoryMethod;

/**
 * autor:liman
 * comment:
 */
public class MathCourseFactory implements CourseFactory {
    @Override
    public ICourse learnCourse() {
        return new MathCourse();
    }
}
