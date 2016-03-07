package com.example.taitran.buzzmovie;

/**
 * Created by John on 3/7/2016.
 */
public class Rating {
    private int score; //score out of 5
    private String comment;

    public Rating(int score, String comment)
    {
        this.score = score;
        this.comment = comment;
    }
    
    public int getScore()
    {
        return score;
    }

    public String getComment()
    {
        return comment;
    }
}
