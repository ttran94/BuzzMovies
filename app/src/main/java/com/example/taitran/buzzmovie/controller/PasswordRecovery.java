package com.example.taitran.buzzmovie.controller;

import android.content.Intent;
import android.database.Cursor;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.net.Uri;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.SendMail;
import com.example.taitran.buzzmovie.model.User;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

import java.util.Properties;


public class PasswordRecovery extends AppCompatActivity {
    private EditText user;
    private UserManagement usermng;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_password_recovery);
        user = (EditText) findViewById(R.id.pwRecovery);
        usermng = new UserManager(this);
    }

    public void RecoveryButtonPressed(View view) {
        if (usermng.passwordRecovery(user.getText().toString()) != null) {
            User getUser = usermng.passwordRecovery(user.getText().toString());
            String adminEmail = "taitrancs94@gmail.com";
            String subject = "BuzzMovie Account Support";
            String[] title = {"BuzzMovie"};
            String setText = String.format("Dear BuzzMovie User,\n\nA forgot password request has been received. " +
                    "If you did not request this change, then please ignore this email" +
                    "\n\nYour account username : %s\n" +
                    "Your password : %s\n\nThank you for using Buzzmovie,\nBuzzmovie Support", getUser.getUsername(), getUser.getPassword());
            SendMail sm = new SendMail(this, getUser.getEmail().trim(), subject.trim(), setText.trim());
            sm.execute();
            finish();
        } else {
                user.setError("Your email/username either does not exist or invalid");
        }
    }
}
