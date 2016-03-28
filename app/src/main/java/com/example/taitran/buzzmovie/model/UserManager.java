package com.example.taitran.buzzmovie.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
/**
 * Created by taitr on 2/6/2016.
 */
public class UserManager implements UserAuthentication, UserManagement{
    private static String[] majors
            = new String[] {"CS", "EE", "ME", "ISYE", "Math", "Phys", "Chem", "ChemE"};
    private static User activeUser;
    private Database db;

    public UserManager() {

    }

    /**
     * take in the activity context and pass it in database class
     * @param context the activity class
     */
    public UserManager(Context context) {
        db = new Database(context);
    }

    public void addUser(String email, String username, String password, String type) {
        if(!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email address");
        }
        if (!db.IsEmpty(username)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (username.length() > 16) {
            throw new IllegalArgumentException("Username is too long.");
        }
        if(username.length() == 0 || username.length() <= 6) {
            throw new IllegalArgumentException("Username is too short.");
        }
        if(username.contains(" ")) {
            throw new IllegalArgumentException("Invalid Username");
        }
        if (password.length() == 0 || password.length() > 20) {
            throw new IllegalArgumentException("Invalid password");
        }

        db.addUser(username, password, email, type);
    }


    /**
     * check whether or not the credential is valid
     * when dealing with Cursor object always check for null to void SQLite EXCEPTION
     * if only have one row of data, then just move the cursor to that row.
     * @param username take in user's username
     * @param password take in user's password
     */
    public void loginRequest(String username, String password) {
        String name = "";
        String pass = "";
        String email = "";
        String major = "";
        String bio = "";
        String status = "";
        String type = "";

        if (db.IsEmpty(username)) { //userId method couldn't find matching username
            throw new IllegalArgumentException("Username does not exist.");
        }
        Cursor data = db.getUserData(username);
        if( data != null && data.moveToFirst() ) {
            name = data.getString(data.getColumnIndex(db.username));
            pass = data.getString(data.getColumnIndex(db.password));
            email = data.getString(data.getColumnIndex(db.email));
            major = data.getString(data.getColumnIndex(db.major));
            bio = data.getString(data.getColumnIndex(db.bio));
            status = data.getString(data.getColumnIndex(db.status));
            type = data.getString(data.getColumnIndex(db.user_type));
            //TODO if status = locked/banned...
            data.close();
        }
        User user = new User(email, name, pass, type);
        if (!user.passwordHandler(password)) {
            throw new IllegalArgumentException("Incorrect password");
        } else if (status.equals(db.locked)) {
            throw new IllegalArgumentException("Your account has been locked");
        } else if (status.equals(db.banned)) {
            throw new IllegalArgumentException("Your account has been banned");
        }
        user.setBio(bio);
        user.setMajor(major);
        activeUser = user;
    }

    /**
     *
     * @return the active user after login successfully
     */
    public User getActiveUser() {
        return activeUser;
    }

    public void logOut() {
        activeUser = null;
    }

    public String[] getMajors() {
        return majors;
    }

    public boolean updatePassword(String oldPass, String newPass) {
        boolean passCheck = activeUser.setPassword(activeUser.getPassword(), newPass);
        if (passCheck) {
            db.setPassword(newPass, activeUser.getUsername());
            return true;
        } else {
            return false;
        }
    }

    public void updateEmail(String email) {
        activeUser.setEmail(email);
        db.setEmail(email, activeUser.getUsername());
    }

    public void updateBio(String bio) {
        activeUser.setBio(bio);
        db.setBio(bio, activeUser.getUsername());
    }

    public void updateMajor(String major) {
        activeUser.setMajor(major);
        db.setMajor(major, activeUser.getUsername());
    }

    /**
     * calls the database for a list of users in the system
     * @param statusFilter Default, Unlocked, Locked, Banned
     * @return list of user strings filtered by status
     */
    public ArrayList<String> getUserList(String statusFilter) {
        return db.getUserList(statusFilter);
    }

    /**
     * calls the database to update the status of a user
     * @param username user to update status for
     * @param status status to change to
     */
    public void updateUserStatus(String username, String status) {
        db.updateUserStatus(username, status);
    }

}