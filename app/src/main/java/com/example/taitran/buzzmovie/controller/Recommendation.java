package com.example.taitran.buzzmovie.controller;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.Adapter;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.taitran.buzzmovie.model.FilterList;
import com.example.taitran.buzzmovie.model.FilterMovie;
import com.example.taitran.buzzmovie.model.RedAdapter;
import com.example.taitran.buzzmovie.model.myAdapter;

import java.util.ArrayList;
import java.util.List;

public class Recommendation extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private Spinner majorSpinner;
    private Spinner ratingSpinner;
    private FilterMovie filter;
    private static final String[] majors
            = new String[] {"Default", "CS", "EE", "ME", "ISYE", "Math", "Phys", "Chem", "ChemE"};
    private static final String[] rating
            = new String[] {"Default", "5", "4", "3", "2", "1"};
    public RedAdapter myadapter;
    private ArrayList<FilterList> movies;
    private RecyclerView viewList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recommendation);
        majorSpinner = (Spinner) findViewById(R.id.spinner2);
        ratingSpinner = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this, R.layout.spinner_item, majors);
        majorSpinner.setAdapter(adapter);
        majorSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<String>(this, R.layout.spinner_item, rating);
        ratingSpinner.setAdapter(adapter1);
        ratingSpinner.setOnItemSelectedListener(this);
        filter = new FilterMovie(majorSpinner.getSelectedItem().toString(), ratingSpinner.getSelectedItem().toString(), this);
        movies = new ArrayList<>();
        viewList = ((RecyclerView) findViewById(R.id.filterList));
        myadapter = new RedAdapter(this);
        viewList.setLayoutManager(new LinearLayoutManager(this));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinnerID = (Spinner) parent;
        if (spinnerID.getId() == R.id.spinner2) {
            filter.updateMajor(majorSpinner.getSelectedItem().toString());
        } else if (spinnerID.getId() == R.id.spinner3) {
            filter.updateRating(ratingSpinner.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    public void filterButtonPressed(View v) {
        movies = filter.getMovieList();
        myadapter.setMovieList(movies);
        viewList.setAdapter(myadapter);
    }
}
