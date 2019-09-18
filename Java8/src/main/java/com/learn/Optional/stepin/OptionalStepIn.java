package com.learn.Optional.stepin;

import com.learn.Optional.common.Car;
import com.learn.Optional.common.Insurance;
import com.learn.Optional.common.Person;

import java.util.Optional;

/**
 * autor:liman
 * createtime:2019/9/10
 * comment:
 */
public class OptionalStepIn {

    public static void main(String[] args) {
        filterOptional();

        optionalIsPresent();
    }

    public static void createOption() {
        Optional<Insurance> emptyInsurance = Optional.<Insurance>empty();
        emptyInsurance.get();//这里会报错。

        Optional.of(new Insurance());//如果为空的话，会抛出NPE

        final Optional<Object> o = Optional.ofNullable(null);//这里构建可以为空

    }

    public static void optionalGet() {
        Optional<Object> nullOptional = Optional.ofNullable(null);//这里构建可以为空
        nullOptional.orElseGet(Insurance::new);
        nullOptional.orElse(new Insurance());
        nullOptional.orElseThrow(RuntimeException::new);
        nullOptional.orElseThrow(() -> new RuntimeException("Not have reference"));
    }

    public static void filterOptional() {
        Optional<Insurance> insurance = Optional.of(new Insurance());//如果为空的话，会抛出NPE

        Insurance nullIns = insurance.filter(i -> i.getName() == null).get();
        System.out.println(nullIns);

        Insurance newIns = insurance.filter(i -> i.getName() != null).orElseGet(Insurance::new);
        System.out.println(newIns);

        String empty_value = insurance.map(i -> i.getName()).orElse("empty value");
        System.out.println(empty_value);
    }

    public static void optionalIsPresent() {
        Optional<Insurance> insurance = Optional.of(new Insurance());//如果为空的话，会抛出NPE
        boolean present = insurance.isPresent();
        System.out.println(present);

        insurance.ifPresent(System.out::println);
    }

    public static String getInsuranceName(Insurance insurance) {
        if (null == insurance) {
            return "nuknow";
        }
        return insurance.getName();
    }

    public static String getInsuranceNameOptional(Insurance insurance) {
        return Optional.ofNullable(insurance).map(Insurance::getName).orElse("unknow");
    }

    public static String getInsuranceNameByMultiExit(Person person) {
        final String defalutValue = "UNKNOW";
        if (null == person)
            return defalutValue;
        Car car = person.getCar();
        if (null == car)
            return defalutValue;
        Insurance insurance = car.getInsurance();
        if (insurance == null)
            return defalutValue;
        return insurance.getName();
    }

}
