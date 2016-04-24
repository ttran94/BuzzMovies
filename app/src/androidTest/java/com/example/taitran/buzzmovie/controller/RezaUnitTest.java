package com.example.taitran.buzzmovie.controller;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.test.AndroidTestCase;

import com.example.taitran.buzzmovie.model.Database;
import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.Rating;

import org.junit.Test;
/**
 * Created by Reza on 4/10/2016.
 */
public class RezaUnitTest extends AndroidTestCase{
    Database database;
    SQLiteDatabase db;

    @Override
    public void setUp() throws Exception{
        super.setUp();
        database = new Database(getContext());
        db = database.getWritableDatabase();
        //add some users
        database.addUser("user1", "123", "user1@gatech.edu", "User");
        database.setMajor("CS", "user1");
    }

    @Test
    public void test_isEmptyTrue() throws Exception {
        Cursor user_cursor = db.rawQuery("SELECT * " +
                "FROM User " +
                "WHERE name=?", new String[]{"user1"});
        assertEquals(true, user_cursor.moveToFirst());
    }

    @Test
    public void test_isEmptyFalse() throws Exception {
        Cursor user_cursor = db.rawQuery("SELECT * " +
                "FROM User " +
                "WHERE name=?", new String[]{"user1"});
        assertEquals(false, user_cursor.moveToFirst());
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();
    }
}
