package com.example.taitran.buzzmovie.controller;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;


public class EditPasswordActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_password);
    }

    /**
     * Method for when the cancel button is pressed.
     * @param v reference for the view for the cancel button.
     */
    public void cancelButtonPressed(View v) {
        Log.d("Edit Password Activity", "Cancel button pressed");
        Intent editActivity = new Intent(this, Dashboard.class);
        editActivity.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(editActivity);
        CharSequence text = "Canceled";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast message = Toast.makeText(context, text, duration);
        message.show();
    }

    /**
     * Method for when the submit button is pressed
     * @param v Reference for the view for the submit button.
     */
    public void submitButtonPressed(View v) {
        Log.d("Edit Password Activity", "Submit button pressed");
        UserManagement userMan = new UserManager(this);
        String oldPass = ((EditText) findViewById(R.id.oldPassText)).getText().toString();
        String newPass = ((EditText) findViewById(R.id.newPassText)).getText().toString();
        String newPassVerify = ((EditText) findViewById(R.id.newPassVerifyText)).getText().toString();

        CharSequence text;

        if (!newPass.equals(newPassVerify)) {
            text = "Passwords do not match.";
        } else if (!userMan.updatePassword(oldPass, newPass)) {
            text = "Wrong password";
        } else {
            finish();
            text = "Success";
        }
        int duration = Toast.LENGTH_SHORT;
        Context context = getApplicationContext();
        Toast message = Toast.makeText(context, text, duration);
        message.show();
    }


}