package com.example.taitran.buzzmovie.controller;

import android.test.AndroidTestCase;
import com.example.taitran.buzzmovie.model.Database;
import com.example.taitran.buzzmovie.model.User;
import android.test.RenamingDelegatingContext;

import org.junit.Test;
/**
 * Created by Hassen on 4/8/16.
 */
public class HassenJunitTest extends AndroidTestCase {
    private Database db;

    @Override
    public void setUp() throws Exception {
        super.setUp();
        RenamingDelegatingContext context = new RenamingDelegatingContext(getContext(), "test_");
        db = new Database(context);
        db.onOpen(db.getReadableDatabase());
    }

    @Override
    public void tearDown() throws Exception {
        db.close();
        super.tearDown();

    }

    @Test
    public void testIsUserLoggedIn() {
        assertEquals(false, db.isUserLoggedIn());
        db.addActiveUser(new User("test", "test", "test", "test"), "test");
        assertEquals(true, db.isUserLoggedIn());
    }

}
