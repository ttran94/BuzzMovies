package com.example.taitran.buzzmovie.model;


public class User {
    /**
     * The username
     */
    private final String username;
    /**
     * The user's password
     */
    private String password;
    /**
     * The user's email
     */
    private String email;
    /**
     * The user's major
     */
    private String major;
    /**
     * The user's bio
     */
    private String bio;
    /**
     * The type of user
     */
    private final String type;

    /**
     * a user constructor that will set up the user profile
     * when the object is created
     * @param email of the user
     * @param username of the user
     * @param password of the user
     * @param type of user
     */
    public User(String email, String username,String password, String type) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.major = "";
        this.bio = "";
        this.type = type;
    }

    /**
     * check the user's password in the database
     * @param password that is typed in by the user when trying to login
     * @return whether or not the password is true or false
     */
    public boolean passwordHandler(String password) {
        return this.password.equals(password);
    }

    /**
     * get user's username
     * @return the user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * get user's email
     * @return the user's email
     */
    public String getEmail() {
        return email;
    }

    /**
     * check if the email is valid, then
     * set that email to the user
     * @param s take in an email string
     */
    public void setEmail(String s) {
        if(!s.contains("@") || !s.contains(".")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        email = s;
    }

    /**
     * check if the old password match then
     * change the password to new pass
     * @param oldPass user's old password
     * @param newPass user's new password
     * @return whether or not set password is successful
     */
    public boolean setPassword(String oldPass, String newPass) {
        if (passwordHandler(oldPass)) {
            password = newPass;
            return true;
        }
        return false;
    }

    /**
     * get the user's password'
     * @return user's password
     */
    public String getPassword() {
        return password;
    }

    /**
     * get user's type
     * @return user's type
     */
    public String getType() {
        return type;
    }
    /**
     * set the user's major
     * @param major get the major
     */
    public void setMajor(String major) {
        this.major = major;
    }

    /**
     * get the user's major
     * @return user's major
     */
    public String getMajor() {
        return major;
    }

    /**
     * check if bio length is valid, then
     * change the user's bio
     * @param bio  get the user's bio
     */
    public void setBio(String bio) {
        if (bio.length() > 100) {
            throw new IllegalArgumentException("Too many words");
        }
        this.bio = bio;
    }

    /**
     * get the user's bio
     * @return the user's bio
     */
    public String getBio() {
        return bio;
    }



}
