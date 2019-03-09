package com.learn.designModel.Factory.FactoryMethod;

/**
 * autor:liman
 * comment:
 */
public class FactoryMethodTest {

    public static void main(String[] args) {
        CourseFactory courseFactory = new MathCourseFactory();
        ICourse mathCourse = courseFactory.learnCourse();
        mathCourse.learn();

        courseFactory = new EnglishCourseFactory();
        ICourse englishCourse = courseFactory.learnCourse();
        englishCourse.learn();

        courseFactory = new CodingCourseFactory();
        ICourse codingCourse=courseFactory.learnCourse();
        codingCourse.learn();
    }

}
