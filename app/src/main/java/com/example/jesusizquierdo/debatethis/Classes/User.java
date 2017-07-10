package com.example.jesusizquierdo.debatethis.Classes;

import java.util.ArrayList;

/**
 * Created by Jesus Izquierdo on 5/14/2017.
 */

public class User {
    String firstName;
    String lastName;
    String email;
    ArrayList<String> topicsAgree = new ArrayList<>();
    String userBio;
    String credentails;

    public User(){}

    public User(String name,String lastName, String email) {
        this.firstName = name;
        this.lastName = lastName;
        this.email = email;
    }

    public void setUserBio(String userBio) {
        this.userBio = userBio;
    }

    public String getLastName() {

        return lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getEmail() {
        return email;
    }

    public ArrayList<String> getTopicsAgree() {
        return topicsAgree;
    }

    public String getUserBio() {
        return userBio;
    }

    public String getCredentails() {
        return credentails;
    }
}
