package com.learn.JSON.domain;

import java.util.Arrays;

/**
 * autor:liman
 * comment:
 */
public class Speaker {

    private Integer id;
    private Integer age;
    private String fullName;
    private String[] tags;
    private boolean registered;

    public Speaker() {
    }

    public Speaker(Integer id, Integer age, String fullName, String[] tags, boolean registered) {
        this.id = id;
        this.age = age;
        this.fullName = fullName;
        this.tags = tags;
        this.registered = registered;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    public String[] getTags() {
        return tags;
    }

    public void setTags(String[] tags) {
        this.tags = tags;
    }

    public boolean isRegistered() {
        return registered;
    }

    public void setRegistered(boolean registered) {
        this.registered = registered;
    }

    @Override
    public String toString() {
        return "Speaker{" +
                "id=" + id +
                ", age=" + age +
                ", fullName='" + fullName + '\'' +
                ", tags=" + Arrays.toString(tags) +
                ", registered=" + registered +
                '}';
    }
}
