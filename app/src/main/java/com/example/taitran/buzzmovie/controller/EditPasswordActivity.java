package com.example.taitran.buzzmovie.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

/**
 * Created by John on 2/15/2016.
 */
public class EditPasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
    }

    public void cancelButtonPressed(View v) {
        Log.d("Edit Password Activity", "Cancel button pressed");
        Intent dashboard = new Intent(this, EditProfileActivity.class);
        startActivity(dashboard);
        CharSequence text = "Canceled";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast message = Toast.makeText(context, text, duration);
        message.show();
    }

    public void submitButtonPressed(View v) {
        Log.d("Edit Password Activity", "Submit button pressed");
        UserManagement userMan = new UserManager();
        String oldPass = ((EditText) findViewById(R.id.oldPassText)).getText().toString();
        String newPass = ((EditText) findViewById(R.id.newPassText)).getText().toString();
        String newPassVerify = ((EditText) findViewById(R.id.newPassVerifyText)).getText().toString();

        CharSequence text;

        if (!newPass.equals(newPassVerify)) {
            text = "Passwords do not match.";
        } else if (!userMan.getActiveUser().setPassword(oldPass, newPass)) {
            text = "Wrong password";
        } else {
            Intent intent = new Intent(this, EditProfileActivity.class);
            startActivity(intent);
            text = "Success";
        }
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast message = Toast.makeText(context, text, duration);
        message.show();
    }


}