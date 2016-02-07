package com.example.taitran.buzzmovie.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.UserAuthentication;
import com.example.taitran.buzzmovie.model.UserManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void loginButtonPressed(View v) {
        Log.d("Login Activity", "Login button pressed");
        UserAuthentication user = new UserManager();
        EditText username = (EditText) findViewById(R.id.userName);
        EditText password = (EditText) findViewById(R.id.Pass);
        CharSequence text;

        if(user.loginRequest(username.getText().toString(), password.getText().toString())) {
            text = "Login Success";
        } else {
            text = "Login Failed";
        }
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast message = Toast.makeText(context, text, duration);
        message.show();
    }

    public void cancelButtonPressed(View V) {
        Log.d("Login Activity", "Cancel button pressed");
        ((EditText) findViewById(R.id.userName)).setText("");
        ((EditText) findViewById(R.id.Pass)).setText((""));
    }

}
