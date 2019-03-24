package com.learn.designModel.PrototypePattern.simpleClone;

/**
 * autor:liman
 * comment:
 */
public class Client {

    private Person person;

    public Person getPerson() {
        return person;
    }

    public void setPerson(Person person) {
        this.person = person;
    }

    public Person clone(){
        return person.clone();
    }
}
