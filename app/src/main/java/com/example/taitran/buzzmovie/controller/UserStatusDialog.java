package com.example.taitran.buzzmovie.controller;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.taitran.buzzmovie.model.UserManager;

import java.util.Arrays;

/**
 * Dialog View for presenting user status changes
 * Uses user_status_dialog.xml layout
 */
public class UserStatusDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{

    private Spinner statusSpinner;
    private String newStatus;

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.user_status_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        statusSpinner = (Spinner) v.findViewById(R.id.statusspinner);

        ArrayAdapter<String> statusAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, Arrays.copyOfRange(UsersActivity.statuses, 1, 4));
        statusSpinner.setAdapter(statusAdapter);
        statusSpinner.setOnItemSelectedListener(this);

        ((TextView)v.findViewById(R.id.username)).setText(UsersActivity.usernameSelected);

        //When the settings are submitted
        builder.setView(v).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                UserManager userman = new UserManager(getContext());
                userman.updateUserStatus(UsersActivity.usernameSelected, newStatus);
            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        return builder.create();
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        newStatus = ((TextView) v).getText().toString();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}