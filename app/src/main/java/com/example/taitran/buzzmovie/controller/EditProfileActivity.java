package com.example.taitran.buzzmovie.controller;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

import org.w3c.dom.Text;

/**
 * Created by andie on 2/14/2016.
 */
public class EditProfileActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener{
    private UserManagement userMan;
    private TextView usernameTextView;
    private TextView emailTextView;
    private EditText emailEditText;
    private Button editEmailButton;
    private TextView majorTextView;
    private Spinner spinner;
    private TextView bioTextView;
    private EditText bioEditText;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userMan = new UserManager();

        usernameTextView = (TextView) findViewById(R.id.displayOriginalPasswordText);
        usernameTextView.setText(userMan.getActiveUser().getUsername());

        emailTextView = (TextView) findViewById(R.id.displayEmailText);
        emailTextView.setText(userMan.getActiveUser().getEmail());

        emailEditText = (EditText) findViewById(R.id.editOldPasswordVerifyText);
        emailEditText.setText(userMan.getActiveUser().getEmail());

        editEmailButton = (Button) findViewById(R.id.editEmailBtn);

        editEmailButton.setText("EDIT");

        majorTextView = (TextView) findViewById(R.id.displayMajor);
        majorTextView.setText("Major: " + userMan.getActiveUser().getMajor());

        ((Button) findViewById(R.id.editMajorBtn)).setText("EDIT");

        spinner = (Spinner) findViewById(R.id.spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, android.R.layout.simple_spinner_dropdown_item, userMan.getMajors());
        spinner.setAdapter(adapter);
        spinner.setOnItemSelectedListener(this);

        ((Button) findViewById(R.id.editBioBtn)).setText("EDIT");

        bioTextView = (TextView) findViewById(R.id.displayBioText);
        bioTextView.setText(userMan.getActiveUser().getBio());

        bioEditText = (EditText) findViewById(R.id.editBioText);
        bioEditText.setText(userMan.getActiveUser().getBio());

    }

    public void editEmailButtonPressed(View v) {
        Button b = (Button) v;

        if (b.getText().toString().equals("EDIT")) {
            b.setText("SET ");

            emailEditText.setText(emailTextView.getText().toString());
            emailTextView.setVisibility(View.INVISIBLE);
            emailEditText.setVisibility(View.VISIBLE);
            emailEditText.requestFocus();
        } else {
            CharSequence text = "Email Changed";

            try {
                userMan.getActiveUser().setEmail(emailEditText.getText().toString());
            } catch (IllegalArgumentException e) {
                text = e.getMessage();
            }

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast message = Toast.makeText(context, text, duration);
            message.show();

            emailTextView.setText(userMan.getActiveUser().getEmail());
            emailEditText.setVisibility(View.INVISIBLE);
            emailTextView.setVisibility(View.VISIBLE);
            b.setText("Edit");
        }
    }

    public void editPasswordButtonPressed(View V) {
        Log.d("Edit Profile Activity", "edit password button pressed");
        Intent intent = new Intent(this, EditPasswordActivity.class);
        startActivity(intent);
    }

    public void editMajorButtonPressed(View v) {
        Button b = (Button) v;
        if (b.getText().toString().equals("EDIT")) {
            majorTextView.setText("Major: ");
            spinner.setVisibility(View.VISIBLE);
            b.setText("SET ");
        } else {
            majorTextView.setText("Major: " + userMan.getActiveUser().getMajor());
            spinner.setVisibility(View.GONE);
            b.setText("EDIT");
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast message = Toast.makeText(context, "Major Changed", duration);
            message.show();
        }


    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        userMan.getActiveUser().setMajor(((TextView)v).getText().toString());
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        Toast message = Toast.makeText(context, "Clicked out", duration);
        message.show();
    }

    public void editBioButtonPressed(View v) {
        Button b = (Button) v;
        if (b.getText().toString().equals("EDIT")) {
            b.setText("SET ");
            bioTextView.setVisibility(View.INVISIBLE);
            bioEditText.setVisibility(View.VISIBLE);
            bioEditText.requestFocus();
        } else {
            //TODO check length of bio
            userMan.getActiveUser().setBio(bioEditText.getText().toString());

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast message = Toast.makeText(context, "Bio Changed", duration);
            message.show();

            bioTextView.setText(userMan.getActiveUser().getBio());
            bioEditText.setVisibility(View.INVISIBLE);
            bioTextView.setVisibility(View.VISIBLE);
            b.setText("EDIT");
        }
    }


}
