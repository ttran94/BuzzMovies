package com.example.taitran.buzzmovie.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
/**
 * Created by Tai Tran on 2/6/2016.
 * UserManagerFacade that handles all functionality for the user
 */
@SuppressWarnings("AccessStaticViaInstance")
public class UserManager implements UserAuthentication, UserManagement{


    /**
     * The list of majors
     */
    private static final String[] majors = new String[] {"CS", "EE", "ME", "ISYE", "Math", "Phys", "Chem", "ChemE"};
    /**
     * The active user
     */
    private static User activeUser;
    /**
     * The database
     */
    private Database db;

    /**
     * Empty constructor
     */
    public UserManager() {

    }

    /**
     * take in the activity context and pass it in database class
     * @param context the activity class
     */
    public UserManager(Context context) {
        db = new Database(context);
    }

    @Override
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

    @Override
    public User getActiveUser() {
        return activeUser;
    }


    @Override
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
            String username123 = db.username;
            name = data.getString(data.getColumnIndex(username123));
            pass = data.getString(data.getColumnIndex(db.password));
            email = data.getString(data.getColumnIndex(db.email));
            major = data.getString(data.getColumnIndex(db.major));
            bio = data.getString(data.getColumnIndex(db.bio));
            status = data.getString(data.getColumnIndex(db.status));
            type = data.getString(data.getColumnIndex(db.user_type));
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
        db.addActiveUser(activeUser, status);
    }



    @Override
    public void setActiveUser() {
        String name = "";
        String pass = "";
        String email = "";
        String major = "";
        String bio = "";
        String type = "";
        if(db.isUserLoggedIn()) {
            Cursor data = db.getUser();
            data.moveToFirst();
            name = data.getString(data.getColumnIndex(db.username));
            pass = data.getString(data.getColumnIndex(db.password));
            email = data.getString(data.getColumnIndex(db.email));
            major = data.getString(data.getColumnIndex(db.major));
            bio = data.getString(data.getColumnIndex(db.bio));
            type = data.getString(data.getColumnIndex(db.user_type));
            data.close();
        }
        User newActiveUser = new User(email, name, pass, type);
        newActiveUser.setBio(bio);
        newActiveUser.setMajor(major);
        activeUser = newActiveUser;
    }

    @Override
    public void logOut(User user) {
        activeUser = user;
    }

    @Override
    public void setRating(Rating rating) {
        db.addRating(rating);
    }

    @Override
    public String[] getMajors() {
        return majors;
    }

    @Override
    public boolean updatePassword(String oldPass, String newPass) {
        boolean passCheck = activeUser.setPassword(oldPass, newPass);
        if (passCheck) {
            db.setPassword(newPass, activeUser.getUsername());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void updateEmail(String email) {
        activeUser.setEmail(email);
        db.setEmail(email, activeUser.getUsername());
    }

    @Override
    public void updateBio(String bio) {
        activeUser.setBio(bio);
        db.setBio(bio, activeUser.getUsername());
    }

    @Override
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