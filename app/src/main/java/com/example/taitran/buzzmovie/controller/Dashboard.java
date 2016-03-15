package com.example.taitran.buzzmovie.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.content.Intent;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.UserAuthentication;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

public class Dashboard extends AppCompatActivity{

    private UserManagement userMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userMan = new UserManager();
        ((TextView) findViewById(R.id.welcomeText)).setText(("Welcome, " + userMan.getActiveUser().getUsername()));
    }

    /**
     * clear the active user and redirect the user to welcome page
     * @param v reference to the logout button when press
     */
    public void logoutButtonPressed(View v) {
        Intent welcomePage = new Intent(this, WelcomeActivity.class);
        startActivity(welcomePage);
        userMan.logOut();
        CharSequence text = "Successfully Logged Out";
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast message = Toast.makeText(context, text, duration);
        message.show();
    }

    /**
     * redirect the user to edit profile activity
     * if the button is pressed
     * @param v reference to edit button
     */
    public void editProfileButtonPressed(View v) {
        Intent editProfilePage = new Intent(this, EditProfileActivity.class);
        startActivity(editProfilePage);
    }

    /**
     * redirect the user to search activity
     * if the button is pressed
     * @param v
     */
    public void searchButtonPressed(View v) {
        Log.d("Dashboard Activity", "search button pressed");
        Intent search = new Intent(this, SearchActivity.class);
        startActivity(search);
    }
    public void reOnPressed(View v) {
        Log.d("Dashboard Activity", "search button pressed");
        Intent search = new Intent(this, Recommendation.class);
        startActivity(search);
    }
}
