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
     * set active user for the app
     * @return User Object
     */
    User getActiveUser();

    /**
     * get the majors array
     * @return the array for major
     */
    String[] getMajors();

    /**
     * check if password is matched with old password
     * then update the password with new pass
     * @param oldPass user's old pass
     * @param newPass  user's new pass
     * @return true or false if password is updated sucessfully
     */
    boolean updatePassword(String oldPass,String newPass);

    /**
     * this method get the user info from database
     * and set active user to that user object
     * @param email's user
     * @param name's user
     * @param pass's user
     * @param bio's user
     * @param major's user
     * @param type's user
     */
    void setActiveUser(String email, String name, String pass, String bio, String major, String type);

    /**
     * update user's email
     * @param email of user
     */
    void updateEmail(String email);

    /**
     * update user's major
     * @param major of user
     */
    void updateMajor(String major);

    /**
     * update user's bio
     * @param bio of user
     */
    void updateBio(String bio);

    /**
     * set activeuser to null and log out
     * @param user object which contains user information
     */
    void logOut(User user);
}
