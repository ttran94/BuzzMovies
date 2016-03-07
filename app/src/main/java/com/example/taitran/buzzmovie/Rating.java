package com.example.taitran.buzzmovie;

import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.User;

/**
 * Created by John on 3/7/2016.
 */
public class Rating {
    private int score; //score out of 5
    private String comment;
    private String username;
    private Movie movie;

    public Rating(String username, Movie movie, int score, String comment)
    {
        this.score = score;
        this.comment = comment;
        this.username = username;
        this.movie = movie;
    }
    
    public int getScore()
    {
        return score;
    }

    public String getComment()
    {
        return comment;
    }

    public String getUsername()
    {
        return username;
    }

    public Movie getMovie()
    {
        return movie;
    }
}
