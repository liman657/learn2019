package com.learn.designModel.Factory.simpleFactory;

/**
 * autor:liman
 * comment:简单工厂测试类
 */
public class SimpleFactoryTest {

    public static void main(String[] args) {
        CourseFactory courseFactory = new CourseFactory();
        ICourse course = courseFactory.getCourse("MathCourse");
        course.learn();

        course = courseFactory.getCourse("CodingCourse");
        course.learn();

        //如果需要加入中文课程，在增加了ChineseCourse的同时还要修改CourseFactory的逻辑，扩展性极差
//        course = courseFactory.getCourse("ChineseCourse");
//        course.learn();//会报空指针


        //反射似乎好了许多，扩展的时候，不用修改CourseFactory的逻辑了，扩展性好了点，但是还是无法控制用户传入的参数
        ICourse courseByClassName = courseFactory.getCourseByClassName("com.learn.designModel.Factory.simpleFactory.EnglishCourse");
        courseByClassName.learn();

        ICourse courseByClazz = courseFactory.getCourseByClazz(CodingCourse.class);
        courseByClazz.learn();
    }

}
