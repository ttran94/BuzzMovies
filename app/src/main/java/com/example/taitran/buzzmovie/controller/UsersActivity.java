package com.example.taitran.buzzmovie.controller;

import android.app.DialogFragment;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;
import com.example.taitran.buzzmovie.model.UserManager;
import java.util.ArrayList;

/**
 * Created by andie on 3/17/2016.
 * display user list and handle OnClickListener
 */
public class UsersActivity extends Fragment {
    /**
     * Status of user
     */
    public static final String[] statuses = new String[] {"Default", "Unlocked", "Locked", "Banned"};
    /**
     * Status spinner
     */
    Spinner statusSpinner;
    /**
     * Selected status
     */
    String statusSelected;
    /**
     * User list
     */
    ListView usersListView;
    /**
     * User manager
     */
    UserManager userMan;
    /**
     * User list
     */
    ArrayList<String> userList;
    /**
     * Filter button
     */
    Button bFilter;
    /**
     * Username selected
     */
    public static String usernameSelected;

    /**
     * Empty constructor
     */
    public UsersActivity() {

    }
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.content_users, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        userMan = new UserManager(getActivity());
        statusSpinner = (Spinner) getActivity().findViewById(R.id.status_spinner);
        bFilter = (Button) getActivity().findViewById(R.id.filter);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, statuses);
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
        usersListView = (ListView) getActivity().findViewById(R.id.userView);
        userList = userMan.getUserList(statusSelected);
        usersListView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, userList));
        usersListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                usernameSelected = ((TextView) view).getText().toString().split(" ")[0];
                DialogFragment userDialog = new UserStatusDialog();
                userDialog.show(getActivity().getFragmentManager().beginTransaction(), "Update User Status");
                //TODO update the view so that it shows the new status automatically
            }
        });

         bFilter.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    userList = userMan.getUserList(statusSelected);
                    usersListView.setAdapter(new ArrayAdapter<>(getActivity(), R.layout.list_item, userList));
                }
            });
    }
}
