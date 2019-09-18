package com.learn.Optional.inaction;


import java.util.Optional;

/**
 * autor:liman
 * createtime:2019/9/10
 * comment:optional的综合实例
 */
public class OptionalInAction {

    public static void main(String[] args) {
        String insuranceNameOptional = getInsuranceNameOptional(null);
        System.out.println(insuranceNameOptional);
    }

    public static String getInsuranceNameOptional(Person person) {
        return Optional.ofNullable(person)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName).orElse("UnKnown");
    }

//    public static String getInsuranceNameByMultiExit(Person person) {
//        final String defalutValue = "UNKNOW";
//        if (null == person)
//            return defalutValue;
//        Car car = person.getCar();
//        if (null == car)
//            return defalutValue;
//        Insurance insurance = car.getInsurance();
//        if (insurance == null)
//            return defalutValue;
//        return insurance.getName();
//    }

}
