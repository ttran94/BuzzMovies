package com.example.taitran.buzzmovie.model;

import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.User;

/**
 * Created by John on 3/7/2016.
 */
public class Rating {
    private float score; //score out of 5
    private String comment;
    private String username;
    private Movie movie;

    /**
     * get the info for the rating object
     * @param username of the user
     * @param movie the movie that the user rated
     * @param score rating score of that movie
     * @param comment of the user
     */
    public Rating(String username, Movie movie, float score, String comment)
    {
        this.score = score;
        this.comment = comment;
        this.username = username;
        this.movie = movie;
    }

    /**
     * the score of a specific movie
     * @return the movie score
     */
    public float getScore()
    {
        return score;
    }

    /**
     * the comment of a specific movie
     * @return the movie comment
     */
    public String getComment()
    {
        return comment;
    }

    /**
     * the username of the user who rated
     * the movie
     * @return user's username
     */
    public String getUsername()
    {
        return username;
    }

    /**
     * @return the movie object
     */
    public Movie getMovie()
    {
        return movie;
    }
}
