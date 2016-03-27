package com.example.taitran.buzzmovie.model;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by taitr on 2/28/2016.
 */
public class Movie implements Parcelable{

    private String title;
    private String year;
    private String type;
    private String poster;


    public Movie(Parcel data) {
        title = data.readString();
        year = data.readString();
        type = data.readString();
        poster = data.readString();
    }
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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(title);
        dest.writeString(year);
        dest.writeString(type);
        dest.writeString(poster);
    }

    public static final Parcelable.Creator<Movie> CREATOR
            = new Parcelable.Creator<Movie>() {
        public Movie createFromParcel(Parcel in) {
            return new Movie(in);
        }

        public Movie[] newArray(int size) {
            return new Movie[size];
        }
    };
}
