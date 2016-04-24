package com.example.taitran.buzzmovie.model;

public interface UserManagement {
    /**
     * get user info and add to database
     * @param email user's email
     * @param username user's name
     * @param password user's pass
     * @param type of user
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
     */
    void setActiveUser();

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
     * set active user to null and log out
     * @param user object which contains user information
     */
    void logOut(@SuppressWarnings("SameParameterValue") User user);

    /**
     * update or set the rating for the movie
     * @param rating object that contains movie's rating
     */
    void setRating(Rating rating);

    /**
     * get password from the email or username
     * @param user a string that can be either username or email
     * @return a the user object for that email or username;
     */
    User passwordRecovery(String user);

    User getUser();
}
