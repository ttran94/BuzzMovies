package com.example.taitran.buzzmovie.model;

/**
 * Created by taitr on 2/6/2016.
 */
public interface UserManagement {
    void addUser(String username, String password);
    User userId(String id);
}
