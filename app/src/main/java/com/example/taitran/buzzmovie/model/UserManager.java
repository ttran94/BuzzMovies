package com.example.taitran.buzzmovie.model;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by taitr on 2/6/2016.
 */
public class UserManager implements UserAuthentication, UserManagement{
    private static Map<String, User> userList = new HashMap<>();
    private static User activeUser;

    public User userId(String userId) {
        return userList.get(userId);
    }

    public void addUser(String email, String name, String password) {
        if(userList.containsKey(name)) {
            throw new IllegalArgumentException("Username already exists");
        }
        if (name.length() > 16) {
            throw new IllegalArgumentException("Username is too long.");
        }
        if(!email.contains("@") || !email.contains(".")) {
            throw new IllegalArgumentException("Invalid email address");
        }

        User user = new User(email, name, password);
        userList.put(name, user);
    }

    public boolean isEmpty() {
        return userList.isEmpty();
    }

    public void loginRequest(String username, String password) {
        User user = userId(username);
        if (user == null) { //userId method couldn't find matching username
            throw new IllegalArgumentException("Username does not exist.");
        }
        if (!user.passwordHandler(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }
        activeUser = user;
    }

    public User getActiveUser() {
        return activeUser;
    }

    public void logOut() {
        activeUser = null;
    }

}