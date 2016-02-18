package com.example.taitran.buzzmovie.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by taitran on 2/15/2016.
 * if you want to see how the user table looks like (easy to debug later on) go to:
 * tools>android>android monitor device
 * on the left, look for project package name then click "file explorer" tab
 * go to data/data/project package name/database/ select the database
 * then choose the icon on top right "pull a file from device"
 * go to firefox and install SQLITE MANAGER addon then open the database.
 * or you can use whatever software that can open SQLITE db.
 */
public class Database extends SQLiteOpenHelper{
    private static final String MOVIE_DATABASE = "Movie_Database";
    private static final String USER_TABLE = "User";
    protected static final String username = "username";
    protected static final String password = "password";
    protected static final String email = "email";
    protected static final String major = "major";
    protected static final String bio = "bio";

    //create the database if it doesn't exist;
    //if database exists, then SQLite will know and skip it.
    //Database name must be unique in SQLite
    public Database(Context context) {
        super(context, MOVIE_DATABASE, null, 1);

    }

    //create the User table if it does not exist.
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + USER_TABLE +
                " (_id INTEGER PRIMARY KEY AUTOINCREMENT, username VARCHAR(255), password VARCHAR(255), email VARCHAR(255), major VARCHAR(255), bio VARCHAR(255))");
    }

    //automatically update when we make change to the User table
    //method will be called whenever we insert or update the table
    //When the table is upgraded, its version will increase.
    //By default it is 1;
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldV, int newV) {
        database.execSQL("DROP TABLE IF EXISTS User");
        onCreate(database);
    }

    //Check if the username exists in the database
    //this method is used for both login and register;
    //query explain:
    //Select everything(data) from the User table where username is equal to name.
    //Cursor contains a subset table of the User table
    //Close the cursor after retrieve the data to prevent data leak.
    protected boolean IsEmpty(String name) {
        boolean isEmpty = false;
        SQLiteDatabase data = this.getReadableDatabase();
        Cursor current = data.rawQuery("Select * from " + USER_TABLE + " where " + username + "=?", new String[] { name });
        int value = current.getCount();
        if (value <= 0) {
            isEmpty = true;
        }
        data.close();
        return isEmpty;
    }

    //get the data if username exists
    protected Cursor getData(String name) {
        SQLiteDatabase data = this.getReadableDatabase();
        return data.rawQuery("Select * from " + USER_TABLE + " where " + username + "=?", new String[] { name });
    }

    //insert data to the User table(register)
    protected void insert(String username, String password, String email) {
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues columnIndex = new ContentValues();
        columnIndex.put("username", username);
        columnIndex.put("password", password);
        columnIndex.put("email", email);
        columnIndex.put("major", "");
        columnIndex.put("bio", "");
        data.insert("User", null, columnIndex);
    }

    //update major info to server
    protected void setMajor(String major, String username) {
        String[] selectArgs = new String[] {username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newMajor = new ContentValues();
        newMajor.put("major", major);
        data.update("User", newMajor, "username =?", selectArgs);
    }

    //update bio info to server
    protected void setBio(String bio, String username) {
        String[] selectArgs = new String[] {username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newBio = new ContentValues();
        newBio.put("bio", bio);
        data.update("User", newBio, "username =?", selectArgs);
    }

    //update password info to server
    protected void setPassword(String password, String username) {
        String[] selectArgs = new String[] {username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newPassword = new ContentValues();
        newPassword.put("password", password);
        data.update("User", newPassword, "username =?", selectArgs);
    }

    //update email info to server
    protected void setEmail(String email, String username) {
        String[] selectArgs = new String[] {username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newEmail = new ContentValues();
        newEmail.put("email", email);
        data.update("User", newEmail, "username =?", selectArgs);
    }



}
