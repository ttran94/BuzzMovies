package com.example.taitran.buzzmovie.controller;

import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.taitran.buzzmovie.model.Database;
import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.Rating;

import org.junit.Test;

/**
 * Testing for the ratingActivity method in the Database class
 */


public class AndieUnitTest extends AndroidTestCase{
    SQLiteDatabase db;
    Database database;
    Rating r1;
    Rating r2;
    Rating r3;
    Movie sample_movie;
    int m_id;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        database = new Database(getContext());
        db = database.getWritableDatabase();
        sample_movie = new Movie("A Movie", "1981", "movie", "");
        //add some users
        database.addUser("user1", "123", "user1@gatech.edu", "User");
        database.setMajor("CS", "user1");
        database.addUser("user2", "123", "user2@gatech.edu", "User");
        database.setMajor("CM", "user2");
    }

    /**
     * when a rating is added whose movie is not in the database already
     * the db should create a new movie in the movies table and then add a rating
     * to the ratings table with a reference to the movie
     * @throws Exception
     */
    @Test
    public void test_new_movie() throws Exception {
        r1 = new Rating("user1", sample_movie, 4, "gr8");
        database.addRating(r1);
        //db = database.getWritableDatabase();
        Cursor movie_cursor = db.rawQuery("SELECT * " +
                "FROM Movies " +
                "WHERE title =?", new String[]{sample_movie.getTitle()});
        assertEquals(true, movie_cursor.moveToFirst());
        assertEquals(sample_movie.getTitle(),
                movie_cursor.getString(movie_cursor.getColumnIndex("title")));
        assertEquals(sample_movie.getYear(),
                movie_cursor.getString(movie_cursor.getColumnIndex("date")));
        assertEquals(sample_movie.getType(),
                movie_cursor.getString(movie_cursor.getColumnIndex("type")));
        m_id = movie_cursor.getInt(movie_cursor.getColumnIndex("_id"));
        movie_cursor.close();

        Cursor rating_cursor = db.rawQuery("SELECT * " +
                "FROM Ratings " +
                "WHERE movie_id =?", new String[]{Integer.toString(m_id)});
        assertEquals(true, rating_cursor.moveToFirst());
        assertEquals((int) r1.getScore(),
                Integer.parseInt(rating_cursor.getString(rating_cursor.getColumnIndex("score"))));
        assertEquals(r1.getComment(),
                rating_cursor.getString(rating_cursor.getColumnIndex("comment")));
        rating_cursor.close();
        //db.close();
    }

    /**
     * If another user rates the same movie, the movie_id in the rating table should be the same
     * and there should be only one instance of that movie in the movie table.
     * @throws Exception
     */
    @Test
    public void test_same_movie() throws Exception {
        test_new_movie();
        r2 = new Rating("user2", sample_movie, 3, "okay");
        database.addRating(r2);
        //db = database.getWritableDatabase();
        Cursor movie_cursor = db.rawQuery("SELECT " +
                "COUNT(*) " +
                "FROM Movies " +
                "WHERE title =? ", new String[]{sample_movie.getTitle()});
        assertEquals(true, movie_cursor.moveToFirst());
        //should only be one movie in table
        assertEquals(1, movie_cursor.getInt(0));
        movie_cursor.close();


        Cursor rating_cursor = db.rawQuery("SELECT " +
                "score, " +
                "comment " +
                "FROM Ratings " +
                "WHERE movie_id =? " +
                "ORDER BY user_id", new String[]{Integer.toString(m_id)});
        assertEquals(true, rating_cursor.moveToFirst());
        assertEquals((int) r1.getScore(), Integer.parseInt(rating_cursor.getString(0)));
        assertEquals(r1.getComment(), rating_cursor.getString(1));
        //should be 2 entries in rating with same movie_id with user1 and user2's ratings
        assertEquals(true, rating_cursor.moveToNext());
        assertEquals((int) r2.getScore(), Integer.parseInt(rating_cursor.getString(0)));
        assertEquals(r2.getComment(), rating_cursor.getString(1));
        rating_cursor.close();
        //db.close();
    }

    /**
     * When the same user adds a new review for the same movie the database should update
     * the user's review rather than creating a new one
     * @throws Exception
     */

    @Test
    public void test_changed_review() throws Exception {
        test_new_movie();
        r3 = new Rating("user1", sample_movie, 2, "meh");
        database.addRating(r3);
        //db = database.getWritableDatabase();
        Cursor count_cursor = db.rawQuery("SELECT " +
                "COUNT(*) " +
                "FROM Ratings " +
                "WHERE movie_id =? AND user_major =? ", new String[]{Integer.toString(m_id), "CS"});
        assertEquals(true, count_cursor.moveToFirst());
        //should only be one review for that user
        assertEquals(1, count_cursor.getInt(0));
        count_cursor.close();

        Cursor rating_cursor = db.rawQuery("SELECT * " +
                "FROM Ratings " +
                "WHERE movie_id =? AND user_major =? ", new String[]{Integer.toString(m_id), "CS"});
        assertEquals(true, rating_cursor.moveToFirst());
        assertEquals((int) r3.getScore(),
                Integer.parseInt(rating_cursor.getString(rating_cursor.getColumnIndex("score"))));
        assertEquals(r3.getComment(),
                rating_cursor.getString(rating_cursor.getColumnIndex("comment")));
        rating_cursor.close();
        //db.close();
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }
}