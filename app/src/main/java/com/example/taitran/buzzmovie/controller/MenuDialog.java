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

/**
 * Dialog View for presenting search settings
 * Uses search_dialog.xml layout
 */
public class MenuDialog extends DialogFragment implements AdapterView.OnItemSelectedListener{

    private Spinner typespinner;
    private static final String[] typeList = new String[]{"All", "Movie", "Series", "Episode"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.search_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        typespinner = (Spinner) v.findViewById(R.id.typespinner);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_dropdown_item, typeList);
        typespinner.setAdapter(typeAdapter);
        typespinner.setOnItemSelectedListener(this);

        //When the settings are submitted
        builder.setView(v).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        return builder.create();
    }

    //When an ordering is selected to sort results by
    @Override
    public void onItemSelected(AdapterView<?> parent, View v, int pos, long id) {
        String text = ((TextView) v).getText().toString();

        for (int i = 0; i < typeList.length; i++) {
            if (typeList[i].equals(text)) {
                SearchActivity.searchType = i;
            }
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {}
}