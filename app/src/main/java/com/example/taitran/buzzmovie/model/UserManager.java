package com.example.taitran.buzzmovie.model;

import java.util.HashMap;
import java.util.Map;
/**
 * Created by taitr on 2/6/2016.
 */
public class UserManager implements UserAuthentication, UserManagement{
    private static Map<String, User> userList = new HashMap<>();

    public User userId(String userId) {
        return userList.get(userId);
    }

    public void addUser(String email, String name, String password) {
        if(userList.containsKey(name)) {
            throw new IllegalArgumentException("Username already exists");
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

    public User loginRequest(String username, String password) {
        User user = userId(username);

        if (user == null) {
            throw new IllegalArgumentException("Username does not exist.");
        }
        if (!user.passwordHandler(password)) {
            throw new IllegalArgumentException("Incorrect password");
        }
        return user;
    }

}