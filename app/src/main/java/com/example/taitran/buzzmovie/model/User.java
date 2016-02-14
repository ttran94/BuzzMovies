package com.example.taitran.buzzmovie.model;

/**
 * Created by taitr on 2/6/2016.
 */
public class User {
    String username;
    String password;
    String email;

    public User(String email, String username,String password) {
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public boolean passwordHandler(String password) {
        return this.password.equals(password);
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }
}
