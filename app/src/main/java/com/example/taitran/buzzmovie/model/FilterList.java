package com.example.taitran.buzzmovie.model;

/**
 * Created by taitr on 3/14/2016.
 */
public class FilterList {
    private String title;
    private String year;
    private String type;
    /* TODO store as a bitmap instead of querying the URL each time*/
    private String poster;
    private float score;

    /**
     *
     * @param title get the filter title
     * @param year  get the filter year
     * @param type  get the filter type
     * @param poster get the filter poster
     */
    public FilterList(String title, String year, String type, String poster, float score) {
        this.title = title;
        this.year = year;
        this.type = type;
        this.poster = poster;
        this.score = score;
    }

    /**
     *
     * @return Title for the filter object
     */
    public String getTitle() {
        return title;
    }

    /**
     *
     * @return year for the filter object
     */
    public String getYear() {
        return year;
    }

    /**
     *
     * @return type for the filter object
     */
    public String getType() {
        return type;
    }

    /**
     *
     * @return url thumbnail for the filter object
     */
    public String getPoster() {
        return poster;
    }

    public float getScore() {
        return score;
    }
}
