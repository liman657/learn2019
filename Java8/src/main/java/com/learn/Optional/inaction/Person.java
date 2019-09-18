package com.learn.Optional.inaction;

import java.util.Optional;

/**
 * autor:liman
 * createtime:2019/9/10
 * comment:
 */
public class Person {

    private Optional<Car> car;

    public Optional<Car> getCar() {
        return car;
    }

    public void setCar(Optional<Car> car) {
        this.car = car;
    }
}
