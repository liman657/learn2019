package com.learn.designModel.Factory.FactoryMethod;

/**
 * autor:liman
 * comment:
 */
public class EnglishCourseFactory implements CourseFactory {
    @Override
    public ICourse learnCourse() {
        return new EnglishCourse();
    }
}
