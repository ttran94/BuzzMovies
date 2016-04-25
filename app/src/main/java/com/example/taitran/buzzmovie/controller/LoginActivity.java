package com.example.taitran.buzzmovie.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.util.Log;
import android.widget.EditText;
import android.content.Context;
import android.widget.Toast;
import android.content.Intent;

import com.example.taitran.buzzmovie.model.UserAuthentication;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

public class LoginActivity extends AppCompatActivity {
    private static int attemptCount = 0;
    private String current = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    /**
     * check whether or not the username and password
     * match the username and password that is in the database
     * @param v reference to the login button
     */
    public void loginButtonPressed(View v) {
        Log.d("Login Activity", "Login button pressed");
        UserAuthentication user = new UserManager(this);
        UserManagement activeUser = new UserManager();
        EditText username = (EditText) findViewById(R.id.userName);
        EditText password = (EditText) findViewById(R.id.Pass);
        CharSequence text;

        text = "Login Success";

        try {
            user.loginRequest(username.getText().toString(), password.getText().toString());
            String type = activeUser.getActiveUser().getType();
            if("User".equals(type)) {
                Intent intent = new Intent(this, Dashboard.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            } else if ("Admin".equals(type)) {
                Intent intent = new Intent(this, AdminActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
                intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
            }

        } catch (IllegalArgumentException e) {
            text = e.getMessage();
            //this is where you would increment an attempted login
            //if the message is "incorrect password"
            //see UserManager -> loginRequest for more
            if ("Incorrect password".equals(e.getMessage())) {
                if(attemptCount == 3) {
                    user.updateUserStatus(username.getText().toString(), "Locked");
                }else {
                    if (attemptCount == 0) {
                        current = username.getText().toString();
                        attemptCount++;
                    } else {
                        if (!current.equals(username.getText().toString())) {
                            attemptCount = 0;
                            current = username.getText().toString();
                            attemptCount++;
                        } else {
                            attemptCount++;
                        }
                    }

                }
            }
        }
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast message = Toast.makeText(context, text, duration);
        message.show();
    }

    /**
     * redirect the user to welcome activity
     * if the button is pressed
     * @param v reference to the cancel button
     */
    public void cancelButtonPressed(View v) {
        Log.d("Login Activity", "Cancel button pressed");
        ((EditText) findViewById(R.id.userName)).setText("");
        ((EditText) findViewById(R.id.Pass)).setText((""));
        finish();
        CharSequence text = "Canceled";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast message = Toast.makeText(context, text, duration);
        message.show();
    }

    /**
     * Log what text you clicked.
     * @param v reference to the button
     */
    public void logTextClicked(View v) {
        finish();
        Intent intent = new Intent(this, RegisterActivity.class);
        startActivity(intent);
    }

    public void pwButton(View view) {
        Intent intent = new Intent(this, PasswordRecovery.class);
        startActivity(intent);
    }
}
