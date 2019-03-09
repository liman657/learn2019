package com.learn.designModel.Factory.simpleFactory;

import org.apache.commons.lang.StringUtils;

/**
 * autor:liman
 * comment: 创建课程的工厂
 */
public class CourseFactory {

    public ICourse getCourse(String courseName) {
        if ("English".equals(courseName)) {
            return new EnglishCourse();
        } else if ("MathCourse".equals(courseName)) {
            return new MathCourse();
        } else if ("CodingCourse".equals(courseName)) {
            return new CodingCourse();
        } else {
            return null;
        }
    }

    /**
     * 消除if else 利用反射
     *
     * @param className
     * @return
     */
    public ICourse getCourseByClassName(String className) {
        try {
            if (StringUtils.isNotBlank(className)) {
                Class<?> aClass = Class.forName(className);
                return (ICourse) aClass.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    /**
     * 直接根据类对象来进行反射
     * @param clazz
     * @return
     */
    public ICourse getCourseByClazz(Class clazz) {
        try {
            if (clazz != null) {
                return (ICourse) clazz.newInstance();
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

}
