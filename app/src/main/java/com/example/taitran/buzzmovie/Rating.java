package com.example.taitran.buzzmovie;

import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.User;

/**
 * Created by John on 3/7/2016.
 */
public class Rating {
    private int score; //score out of 5
    private String comment;
    private User user;
    private Movie movie;

    public Rating(User user, Movie movie, int score, String comment)
    {
        this.score = score;
        this.comment = comment;
        this.user = user;
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

    public User getUser()
    {
        return user;
    }

    public Movie getMovie()
    {
        return movie;
    }
}
