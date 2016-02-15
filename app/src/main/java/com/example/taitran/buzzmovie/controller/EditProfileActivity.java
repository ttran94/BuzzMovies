package com.example.taitran.buzzmovie.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.TextView;

import com.example.taitran.buzzmovie.controller.R;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

/**
 * Created by andie on 2/14/2016.
 */
public class EditProfileActivity extends AppCompatActivity {
    private UserManagement userMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userMan = new UserManager();
        ((TextView) findViewById(R.id.displayUsernameText)).setText((userMan.getActiveUser().getUsername()));
        ((TextView) findViewById(R.id.displayEmailText)).setText((userMan.getActiveUser().getEmail()));
    }


}