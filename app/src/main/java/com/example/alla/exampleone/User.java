package com.example.alla.exampleone;

public class User {
    private String fistName;
    private String lastName;

    public User (String firstName, String lastName) {
        this.fistName = firstName;
        this.lastName = lastName;
    }
    public User() {}

    public String getLastName() {return lastName;}
    public String getFistName() {return fistName;}
    public void setFistName(String name) {fistName = name;}
    public void setLastName(String name) {lastName = name;}
}