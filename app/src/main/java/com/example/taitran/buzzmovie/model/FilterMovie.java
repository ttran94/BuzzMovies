package com.example.taitran.buzzmovie.model;

import android.content.Context;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class FilterMovie {
    /**
     * The major
     */
    private String major;
    /**
     * The rating
     */
    private String rating;
    /**
     * The database
     */
    private final Database db;

    /**
     * Filtering the movies
     * @param major the movie major
     * @param rating the rating
     * @param context the context in the database
     */
    public FilterMovie(String major , String rating, Context context) {
        this.major = major;
        this.rating = rating;
        db = new Database(context);
    }

// --Commented out by Inspection START (4/10/2016 10:29 PM):
//    /**
//     * Return the major
//     * @return major
//     */
//    public String getMajor() {
//        return major;
//    }
// --Commented out by Inspection STOP (4/10/2016 10:29 PM)

// --Commented out by Inspection START (4/10/2016 9:55 PM):
//    /**
//     * Return the rating
//     * @return rating
//     */
//    public String getRating() {
//        return rating;
//    }
// --Commented out by Inspection STOP (4/10/2016 9:55 PM)

    /**
     * Update the major
     * @param major the new major
     */
    public void updateMajor(String major) {
        this.major = major;
    }

    /**
     * Update the rating
     * @param rating the new rating
     */
    public void updateRating(String rating) {
        this.rating = rating;
    }

    /**
     * The movie list
     * @return the list of movies
     */
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
