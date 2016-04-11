package com.example.taitran.buzzmovie.model;


public class FilterList {
    /**
     * The title
     */
    private final String title;
    /**
     * The year
     */
    private final String year;
    /**
     * The type
     */
    private final String type;
    /**
     * The poster
     */
    private final String poster;
    /**
     * The score
     */
    private final float score;

    /**
     *
     * @param title get the filter title
     * @param year  get the filter year
     * @param type  get the filter type
     * @param poster get the filter poster
     * @param score get the filter score
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

    /**
     * The score
     * @return score
     */
    public float getScore() {
        return score;
    }
}
