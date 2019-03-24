package com.learn.designModel.PrototypePattern.deepClone;

        import java.io.Serializable;

/**
 * autor:liman
 * comment: 金箍棒
 */
public class GoldenCudgel implements Serializable {

    private double height;
    private double weight;

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
