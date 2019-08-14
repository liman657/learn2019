package com.learn.lambda.common;

/**
 * author: liman
 * createtime: 2019/8/8
 */
public class Apple implements Comparable<Apple>{

    private int weight;
    private String color;
    private String category;

    public Apple() {
    }

    public Apple(int weight) {
        this.weight = weight;
    }

    public Apple(int weight, String color) {
        this.weight = weight;
        this.color = color;
    }

    public Apple(int weight, String color, String category) {
        this.weight = weight;
        this.color = color;
        this.category = category;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return "Apple{" +
                "weight=" + weight +
                ", color='" + color + '\'' +
                ", category='" + category + '\'' +
                '}';
    }

    @Override
    public int compareTo(Apple o) {
        return this.getWeight()-o.getWeight();
    }
}
