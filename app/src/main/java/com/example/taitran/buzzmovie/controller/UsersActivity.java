package com.example.taitran.buzzmovie.controller;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.taitran.buzzmovie.model.UserManager;
import java.util.ArrayList;

/**
 * Created by andie on 3/17/2016.
 */
public class UsersActivity extends AppCompatActivity {

    public static final String[] statuses
            = new String[] {"Default", "Unlocked", "Locked", "Banned"};
    Spinner statusSpinner;
    String statusSelected;
    ListView usersListView;
    UserManager userMan;
    ArrayList<String> userList;
    public static String usernameSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        userMan = new UserManager(this);
        statusSpinner = (Spinner) findViewById(R.id.status_spinner);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, statuses);
        statusSpinner.setAdapter(adapter);
        statusSelected = "Default";
        statusSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                statusSelected = statusSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        usersListView = (ListView) findViewById(R.id.userView);
        userList = userMan.getUserList(statusSelected);
        usersListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, userList));
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usernameSelected = ((TextView)view).getText().toString().split(" ")[0];
                DialogFragment userDialog = new UserStatusDialog();
                userDialog.show(getFragmentManager().beginTransaction(), "Update User Status");
                //TODO update the view so that it shows the new status automatically
            }
        });
    }


    /**
     * filter the users presented based on status
     * @param v View
     */
    public void filterButtonPressed(View v) {
        userList = userMan.getUserList(statusSelected);
        usersListView.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, userList));
    }
}
