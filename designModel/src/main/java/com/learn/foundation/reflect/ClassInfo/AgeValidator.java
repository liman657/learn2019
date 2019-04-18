package com.learn.foundation.reflect.ClassInfo;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * autor:liman
 * comment:
 */

@Retention(RetentionPolicy.RUNTIME)
@Target(value={ElementType.METHOD})
public @interface AgeValidator {
    public int min();
    public int max();
}
