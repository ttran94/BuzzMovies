package com.example.taitran.buzzmovie.model;

public class Rating {
    /**
     * The rating score
     */
    private final float score; //score out of 5
    /**
     * The comment on the rating
     */
    private final String comment;
    /**
     * The user who rated the movie
     */
    private final String username;
    /**
     * The movie that gets rated
     */
    private final Movie movie;

    /**
     * get the info for the rating object
     * @param username of the user
     * @param movie the movie that the user rated
     * @param score rating score of that movie
     * @param comment of the user
     */
    @SuppressWarnings("unused")
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
