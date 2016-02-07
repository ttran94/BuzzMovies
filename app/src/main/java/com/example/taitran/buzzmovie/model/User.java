package com.example.taitran.buzzmovie.model;

/**
 * Created by taitr on 2/6/2016.
 */
public class User {
    String username;
    String password;

    public User(String username,String password) {
        this.username = username;
        this.password = password;
    }

    public boolean passwordHandler(String password) {
        return this.password.equals(password);
    }
}
