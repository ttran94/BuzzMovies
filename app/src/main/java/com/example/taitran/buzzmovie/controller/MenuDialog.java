package com.example.taitran.buzzmovie.controller;

import android.support.v7.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.v7.view.ContextThemeWrapper;
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

    /**
     * All the types of media
     */
    private static final String[] typeList = new String[]{"All", "Movie", "Series", "Episode"};

    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View v = inflater.inflate(R.layout.search_dialog, null);
        AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(getActivity(), R.style.AppCompatAlertDialogStyle));
        Spinner typeSpinner = (Spinner) v.findViewById(R.id.typespinner);

        ArrayAdapter<String> typeAdapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_dropdown_item, typeList);
        typeSpinner.setAdapter(typeAdapter);
        typeSpinner.setOnItemSelectedListener(this);
        builder.setTitle("Type");
        //When the settings are submitted
        builder.setView(v).setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {}
        });
        return builder.show();
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