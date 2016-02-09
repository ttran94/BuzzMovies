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

    public void addUser(String name, String password) {
        User user = new User(name, password);
    }
    public boolean loginRequest(String username, String password) {
        User user = userId(username);
        if (user == null) {
            return false;
        }
        return user.passwordHandler(password);
    }

    public boolean registerRequest(String username) {
        if(userList.containsKey(username)) {
            return false;
        }
        return true;
    }

}

