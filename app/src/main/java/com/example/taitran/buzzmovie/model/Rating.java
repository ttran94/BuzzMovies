package com.example.taitran.buzzmovie.model;

public class Rating {
    /**
     * The rating score
     */
    private float score; //score out of 5
    /**
     * The comment on the rating
     */
    private String comment;
    /**
     * The user who rated the movie
     */
    private String username;
    /**
     * The movie that gets rated
     */
    private Movie movie;

    /**
     * get the info for the rating object
     * @param username of the user
     * @param movie the movie that the user rated
     * @param score rating score of that movie
     * @param comment of the user
     */
    public Rating(String username, Movie movie, float score, String comment) {
        this.score = score;
        this.comment = comment;
        this.username = username;
        this.movie = movie;
    }

    /**
     * the score of a specific movie
     * @return the movie score
     */
    public float getScore() {
        return score;
    }

    /**
     * the comment of a specific movie
     * @return the movie comment
     */
    public String getComment() {
        return comment;
    }

    /**
     * the username of the user who rated
     * the movie
     * @return user's username
     */
    public String getUsername() {
        return username;
    }

    /**
     * @return the movie object
     */
    public Movie getMovie() {
        return movie;
    }
}
