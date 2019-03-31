package com.learn.lambda.chapter02;

/**
 * autor:liman
 * comment:
 */
public class Apple {

    private int weight;
    private String color;
    private boolean flag;

    public Apple(int weight, String color, boolean flag) {
        this.weight = weight;
        this.color = color;
        this.flag = flag;
    }

    public int getWeight() {
        return weight;
    }

    public void setWeight(int weight) {
        this.weight = weight;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", color='" + color + '\'' +
                ", flag=" + flag +
                '}';
    }
}
