package com.learn.Optional.inaction;

import java.util.Optional;

/**
 * autor:liman
 * createtime:2019/9/10
 * comment:
 */
public class Car {

    private Optional<Insurance> insurance;

    public Optional<Insurance> getInsurance() {
        return insurance;
    }

    public void setInsurance(Optional<Insurance> insurance) {
        this.insurance = insurance;
    }
}
