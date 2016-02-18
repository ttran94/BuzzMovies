package com.example.taitran.buzzmovie.model;

/**
 * Created by taitran on 2/6/2016.
 */
public interface UserManagement {
    void addUser(String email, String username, String password);
    User getActiveUser();
    String[] getMajors();
    boolean updatePassword(String oldPass,String newPass);
    void updateEmail(String email);
    void updateMajor(String major);
    void updateBio(String bio);
    void logOut();
}
