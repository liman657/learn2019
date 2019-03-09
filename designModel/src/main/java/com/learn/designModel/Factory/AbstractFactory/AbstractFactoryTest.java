package com.learn.designModel.Factory.AbstractFactory;

/**
 * autor:liman
 * comment:
 */
public class AbstractFactoryTest {

    public static void main(String[] args) {

        CourseFactory courseFactory = new MathCourseFactory();
        ICourse course=courseFactory.createCourse();
        course.learn();

        IPractise practise = courseFactory.createPractise();
        practise.practise();

        IExam exam = courseFactory.createExam();
        exam.exam();

        courseFactory = new CodingCourseFactory();
        course = courseFactory.createCourse();
        course.learn();
        practise = courseFactory.createPractise();
        practise.practise();
        exam = courseFactory.createExam();
        exam.exam();

    }

}
