package com.learn.designModel.PrototypePattern.simpleClone;

import java.util.ArrayList;
import java.util.List;

/**
 * autor:liman
 * comment:浅克隆实例
 */
public class SimpleCloneTest {

    public static void main(String[] args) {

        String name = "test";
        int age = 10;
        List<String> hobbies = new ArrayList<>();

        PersonA person = new PersonA();
        person.setHobbies(hobbies);
        person.setName(name);
        person.setAge(age);

        PersonA newPerson = (PersonA)person.clone();

        System.out.println(newPerson.getHobbies() == person.getHobbies());

//        Client client = new Client();
//        client.setPerson(person);
//
//        PersonA clonePerson = (PersonA)client.clone();
//        System.out.println(clonePerson.getHobbies() == person.getHobbies());

    }

}
