package com.learn.designModel.PrototypePattern.simpleClone;

import java.util.List;

/**
 * autor:liman
 * comment:
 */
public class PersonA implements Person{

    private int age;
    private String name;
    private List<String> hobbies;

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<String> getHobbies() {
        return hobbies;
    }

    public void setHobbies(List<String> hobbies) {
        this.hobbies = hobbies;
    }

    @Override
    public Person clone() {
        try {
            return (Person) super.clone();
        } catch (CloneNotSupportedException e) {
            e.printStackTrace();
            return null;
        }
    }


//    @Override
//    public Person clone() {
//        PersonA newPerson = new PersonA();
//        newPerson.setAge(this.age);
//        newPerson.setName(this.name);
//        newPerson.setHobbies(this.hobbies);
//        return newPerson;
//    }
}
