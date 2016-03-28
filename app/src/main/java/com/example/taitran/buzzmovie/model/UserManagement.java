package com.example.taitran.buzzmovie.model;

/**
 * Created by taitran on 2/6/2016.
 */
public interface UserManagement {
    /**
     * get user info and add to database
     * @param email user's email
     * @param username user's name
     * @param password user's pass
     */
    void addUser(String email, String username, String password, String type);

    /**
     * return active user
     * @return User Object
     */
    User getActiveUser();

    /**
     *
     * @return an array of majors
     */
    String[] getMajors();

    /**
     * change user's password
     * @param oldPass user's old pass
     * @param newPass  user's new pass
     * @return true or false if password is change
     */
    boolean updatePassword(String oldPass,String newPass);
    void updateEmail(String email);
    void updateMajor(String major);
    void updateBio(String bio);
    void logOut();
}
