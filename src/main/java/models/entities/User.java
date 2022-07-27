package models.entities;

import models.Gender;

import static java.util.UUID.randomUUID;

public class User {

    private final String id;

    private String name;

    private Gender gender;

    public User(String name, Gender gender) {
        this.id = randomUUID().toString();
        this.name = name;
        this.gender = gender;
    }

    public String getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Gender getGender() {
        return gender;
    }
}
