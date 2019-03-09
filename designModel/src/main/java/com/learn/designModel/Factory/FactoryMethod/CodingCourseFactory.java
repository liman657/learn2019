package com.learn.designModel.Factory.FactoryMethod;

/**
 * autor:liman
 * comment:
 */
public class CodingCourseFactory implements CourseFactory {
    @Override
    public ICourse learnCourse() {
        return new CodingCourse();
    }
}
