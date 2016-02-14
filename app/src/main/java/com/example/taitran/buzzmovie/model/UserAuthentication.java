package com.example.taitran.buzzmovie.model;

/**
 * Created by taitran on 2/6/2016.
 */
public interface UserAuthentication {
    User loginRequest(String name, String password);
    boolean isEmpty();

}
