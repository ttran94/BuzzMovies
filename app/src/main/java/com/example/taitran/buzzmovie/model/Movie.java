package com.example.taitran.buzzmovie.model;

/**
 * Created by taitr on 2/28/2016.
 */
public class Movie {

    private String title;
    private String year;
    private String type;
    /* TODO store as a bitmap instead of querying the URL each time*/
    private String poster;

/**
*
* @param title get the movie title
* @param year  get the movie year
* @param type  get the movie type
* @param poster    get the movie poster
*/
    public Movie(String title, String year, String type, String poster) {
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
    }

    /**
     *
     * @return Title for the movie object
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return year for the movie object
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @return type for the movie object
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return url thumbnail for the movie object
     */
    public String getPoster() {
        return poster;
    }
}
