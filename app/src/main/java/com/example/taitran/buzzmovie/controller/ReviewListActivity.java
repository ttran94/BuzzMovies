package com.example.taitran.buzzmovie.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;

import com.example.taitran.buzzmovie.model.Database;
import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.myAdapter;

import java.util.ArrayList;


public class ReviewListActivity extends AppCompatActivity{
    /**
     * Major spinner
     */
    private Spinner majorSpinner;
    /**
     * Rating spinner
     */
    private Spinner ratingSpinner;
    /**
     * Review list
     */
    private ListView reviewListView;
    /**
     * Reviews
     */
    private ArrayList<String> reviews;
    /**
     * Majors
     */
    private static final String[] majors = new String[] {"Default", "CS", "EE", "ME", "ISYE", "Math", "Phys", "Chem", "ChemE"};
    /**
     * Ratings
     */
    private static final String[] rating = new String[] {"Default", "5", "4", "3", "2", "1"};
    /**
     * Selected major
     */
    private String majorSelected;
    /**
     * Selected rating
     */
    private String ratingSelected;
    /**
     * Database
     */
    private Database db;
    /**
     * Movie
     */
    private Movie movie;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reviewlayout);

        Bundle extras = getIntent().getExtras();
        int position = (int)extras.get("position");
        movie = myAdapter.getMovieList().get(position);

        ratingSelected = "Default";
        majorSelected = "Default";

        majorSpinner = (Spinner) findViewById(R.id.spinner2);
        ratingSpinner = (Spinner) findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.spinner_item, majors);
        majorSpinner.setAdapter(adapter);
        majorSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                majorSelected = majorSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(this, R.layout.spinner_item, rating);
        ratingSpinner.setAdapter(adapter1);
        ratingSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                ratingSelected = ratingSpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
            }
        });
        reviewListView = (ListView) findViewById(R.id.reviewList);
        reviewListView.setVisibility(View.VISIBLE);
        ((RecyclerView) findViewById(R.id.filterList)).setVisibility(View.GONE);
        db = new Database(this);
        reviews = db.getRatings(movie, ratingSelected, majorSelected);
        reviewListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, reviews));
    }

    /**
     * Filter button pressed
     * @param v reference to the filter button
     */
    public void filterButtonPressed(View v) {
        reviews = db.getRatings(movie, ratingSelected, majorSelected);
        reviewListView.setAdapter(new ArrayAdapter<>(this, android.R.layout.simple_selectable_list_item, reviews));
    }
}
