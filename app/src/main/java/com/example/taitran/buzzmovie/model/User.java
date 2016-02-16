package com.example.taitran.buzzmovie.model;

/**
 * Created by taitr on 2/6/2016.
 */


public class User {
    private String username;
    private String password;
    private String email;
    private String major;
    private String bio;

    public User(String email, String username,String password) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.major = "";
        this.bio = "";
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

    public void setEmail(String s) {
        if(!s.contains("@") || !s.contains(".")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        email = s;
    }

    public boolean setPassword(String oldPass, String newPass) {
        if (passwordHandler(oldPass)) {
            password = newPass;
            return true;
        }
        return false;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMajor() {
        return major;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public String getBio() {
        return bio;
    }

}
