package com.example.taitran.buzzmovie.model;

import android.content.Context;
import android.database.Cursor;

import java.util.HashMap;
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

    public UserManager(Context context) {
        db = new Database(context);
    }

    public void addUser(String email, String username, String password) {
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

        db.insert(username, password, email);
    }

    //when dealing with Cursor object always check for null to void SQLite EXCEPTION
    //if only have one row of data, then just move the cursor to that row.
    public void loginRequest(String username, String password) {
        String name = "";
        String pass = "";
        String email = "";
        String major = "";
        String bio = "";

        if (db.IsEmpty(username)) { //userId method couldn't find matching username
            throw new IllegalArgumentException("Username does not exist.");
        }
        Cursor data = db.getData(username);
        if( data != null && data.moveToFirst() ) {
            name = data.getString(data.getColumnIndex(db.username));
            pass = data.getString(data.getColumnIndex(db.password));
            email = data.getString(data.getColumnIndex(db.email));
            major = data.getString(data.getColumnIndex(db.major));
            bio = data.getString(data.getColumnIndex(db.bio));
            data.close();
        }
        User user = new User(email, name, pass);
        if (!user.passwordHandler(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }
        user.setBio(bio);
        user.setMajor(major);
        activeUser = user;
    }

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

}