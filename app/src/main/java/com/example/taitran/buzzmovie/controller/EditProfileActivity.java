package com.example.taitran.buzzmovie.controller;

import android.app.ActionBar;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taitran.buzzmovie.controller.R;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

/**
 * Created by andie on 2/14/2016.
 */
public class EditProfileActivity extends AppCompatActivity {
    private UserManagement userMan;
    private TextView usernameTextView;
    private TextView emailTextView;
    private EditText emailEditText;
    private Button editEmailButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);
        userMan = new UserManager();

        usernameTextView = (TextView) findViewById(R.id.displayUsernameText);
        usernameTextView.setText(userMan.getActiveUser().getUsername());

        emailTextView = (TextView) findViewById(R.id.displayEmailText);
        emailTextView.setText(userMan.getActiveUser().getEmail());

        emailEditText = (EditText) findViewById(R.id.editEmailText);
        emailEditText.setText(userMan.getActiveUser().getEmail());

        editEmailButton = (Button) findViewById(R.id.editEmailBtn);
        editEmailButton.setText("EDIT");
    }

    public void editEmailButtonPressed(View v) {
        Button b = (Button) v;
        if(b.getText().toString() == "EDIT") {
            b.setText("SET ");
            emailEditText.setText(emailTextView.getText().toString());
            emailTextView.setVisibility(View.INVISIBLE);
            emailEditText.setVisibility(View.VISIBLE);
            emailEditText.requestFocus();
        } else
        {
            CharSequence text = "Email Changed";

            try {
                userMan.getActiveUser().setEmail(emailEditText.getText().toString());
            } catch (IllegalArgumentException e)
            {
                text = e.getMessage();
            }

            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast message = Toast.makeText(context, text, duration);
            message.show();

            emailTextView.setText(userMan.getActiveUser().getEmail());
            emailEditText.setVisibility(View.INVISIBLE);
            emailTextView.setVisibility(View.VISIBLE);
            b.setText("EDIT");
        }

    }


}