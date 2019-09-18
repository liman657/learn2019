package com.learn.collector.common;

/**
 * autor:liman
 * createtime:2019/8/14
 * comment: 菜肴
 */
public class Dish {

    private final String name;//姓名
    private final boolean vegetarian;//是否是蔬菜
    private final int calories;//热量
    private final Type type;//类型

    public Dish(String name, boolean vegetarian, int calories, Type type) {
        this.name = name;
        this.vegetarian = vegetarian;
        this.calories = calories;
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public boolean isVegetarian() {
        return vegetarian;
    }

    public int getCalories() {
        return calories;
    }

    public Type getType() {
        return type;
    }

    public enum Type{MEAT,FISH,OTHER}

    @Override
    public String toString() {
        return "Dish{" +
                "name='" + name + '\'' +
                ", vegetarian=" + vegetarian +
                ", calories=" + calories +
                ", type=" + type +
                '}';
    }
}
