package com.example.taitran.buzzmovie.model;

import android.content.Context;
import android.database.Cursor;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

/**
 * Created by taitr on 3/14/2016.
 */
public class FilterMovie {
    private String major;
    private String rating;
    private Database db;
    public FilterMovie(String major , String rating, Context context) {
        this.major = major;
        this.rating = rating;
        db = new Database(context);
    }

    public String getMajor() {
        return major;
    }

    public String getRating() {
        return rating;
    }

    public void updateMajor(String major) {
        this.major = major;
    }

    public void updateRating(String rating) {
        this.rating = rating;
    }

    public ArrayList<FilterList> getMovieList() {
        ArrayList<FilterList> movieList = db.getMovieList(major, rating);
        Collections.sort(movieList, new Comparator<FilterList>() {
            @Override
            public int compare(FilterList lhs, FilterList rhs) {
                return Float.compare(rhs.getScore(), lhs.getScore());
            }

        });
        return movieList;
    }
}
