package com.example.taitran.buzzmovie.controller;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.View.OnClickListener;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.Spinner;

import com.example.taitran.buzzmovie.model.FilterList;
import com.example.taitran.buzzmovie.model.FilterMovie;
import com.example.taitran.buzzmovie.model.RedAdapter;

import java.util.ArrayList;


public class Recommendation extends Fragment implements AdapterView.OnItemSelectedListener, OnClickListener {
    /**
     * Major spinner
     */
    private Spinner majorSpinner;
    /**
     * Rating spinner
     */
    private Spinner ratingSpinner;
    /**
     * Filter for the movie
     */
    private FilterMovie filterL;
    /**
     * String list
     */
    private static final String[] majors = new String[] {"Default", "CS", "EE", "ME", "ISYE", "Math", "Phys", "Chem", "ChemE"};
    /**
     * Rating list
     */
    private static final String[] rating = new String[] {"Default", "5", "4", "3", "2", "1"};
    /**
     * Adapter
     */
    private RedAdapter myAdapter;
    /**
     * Movie list
     */
    private ArrayList<FilterList> movies;
    /**
     * Recycle view
     */
    private RecyclerView viewList;

    /**
     * Empty constructor
     */
    public Recommendation() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.activity_recommendation, container, false);
    }


    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        Button filterButton = (Button) getActivity().findViewById(R.id.filterB);
        filterButton.setOnClickListener(this);
        majorSpinner = (Spinner) getActivity().findViewById(R.id.spinner2);
        ratingSpinner = (Spinner) getActivity().findViewById(R.id.spinner3);
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, majors);
        majorSpinner.setAdapter(adapter);
        majorSpinner.setOnItemSelectedListener(this);
        ArrayAdapter<String> adapter1 = new ArrayAdapter<>(getActivity(), R.layout.spinner_item, rating);
        ratingSpinner.setAdapter(adapter1);
        ratingSpinner.setOnItemSelectedListener(this);
        filterL = new FilterMovie(majorSpinner.getSelectedItem().toString(), ratingSpinner.getSelectedItem().toString(), getActivity());
        movies = new ArrayList<>();
        viewList = ((RecyclerView) getActivity().findViewById(R.id.filterList));
        myAdapter = new RedAdapter(this.getContext());
        viewList.setLayoutManager(new LinearLayoutManager(getActivity()));
    }


    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        Spinner spinnerID = (Spinner) parent;
        if (spinnerID.getId() == R.id.spinner2) {
            filterL.updateMajor(majorSpinner.getSelectedItem().toString());
        } else if (spinnerID.getId() == R.id.spinner3) {
            filterL.updateRating(ratingSpinner.getSelectedItem().toString());
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.filterB:
                movies = filterL.getMovieList();
                myAdapter.setMovieList(movies);
                viewList.setAdapter(myAdapter);
                break;
        }
    }
}
