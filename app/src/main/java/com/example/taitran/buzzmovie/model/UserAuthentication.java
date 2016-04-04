package com.example.taitran.buzzmovie.model;

/**
 * Created by taitran on 2/6/2016.
 */
public interface UserAuthentication {
    /**
     * check whether or not the credential is valid
     * when dealing with Cursor object always check for null to void SQLite EXCEPTION
     * if only have one row of data, then just move the cursor to that row.
     * @param name take in user's username
     * @param password take in user's password
     */
    void loginRequest(String name, String password);
}
