package com.example.taitran.buzzmovie.controller;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.UserAuthentication;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;


/**
 * A simple {@link Fragment} subclass.
 */
public class AddUser extends Fragment {


    public AddUser() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_add_user, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button regbt = (Button) getActivity().findViewById(R.id.submitBtn);
        regbt.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String type = "Admin";
                Log.d("Register Activity", "Register button pressed");
                UserManagement manager = new UserManager(getActivity());
                EditText username = (EditText) getActivity().findViewById(R.id.regUserName);
                EditText password = (EditText) getActivity().findViewById(R.id.regPass);
                EditText email = (EditText) getActivity().findViewById(R.id.regEmail);
                CharSequence text = "Add Account Success";

                try {
                    manager.addUser(email.getText().toString(), username.getText().toString(), password.getText().toString(), type);
                    username.setText("");
                    password.setText("");
                    email.setText("");
                } catch (IllegalArgumentException e) {
                    text = e.getMessage();
                }
                Context context = getActivity();
                int duration = Toast.LENGTH_SHORT;
                Toast message = Toast.makeText(context, text, duration);
                message.show();
            }
        });
    }

}
