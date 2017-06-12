package com.example.jesusizquierdo.debatethis.Classes;

import java.util.ArrayList;

/**
 * Created by Jesus Izquierdo on 5/14/2017.
 */

public class User {
    String name;
    String email;
    ArrayList<String> topicsAgree = new ArrayList<>();
    String userBio;
    String credentails;

    public User(){}

    public User(String name, String email) {
        this.name = name;
        this.email = email;
    }

    public String getName() {
        return name;
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
