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

    private static final String RATINGS_TABLE = "Ratings";
    private static final String rating = "rating";
    private static final String review = "review";

    private static final String MOVIE_TABLE = "Movies";
    private static final String title = "title";
    private static final String date = "date";
    private static final String type = "type";

    //create the database if it doesn't exist;
    //if database exists, then SQLite will know and open it.
    //Database name must be unique in SQLite
    public Database(Context context) {
        super(context, MOVIE_DATABASE, null, 1);
    }

    //create the User table if it does not exist.
    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL("CREATE TABLE " + USER_TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                username + " VARCHAR(255), " +
                password + " VARCHAR(255), " +
                email + " VARCHAR(255), " +
                major + " VARCHAR(255), " +
                bio + " VARCHAR(255))"); //TODO should we change bio to TEXT?

        database.execSQL("CREATE TABLE " + MOVIE_TABLE + " (" + //TODO maybe add genre/description
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                title + " VARCHAR(255), " +
                date + " INTEGER, " +
                type + " VARCHAR(255))");

        database.execSQL("CREATE TABLE " + RATINGS_TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "movie_id INTEGER, " +
                "user_id INTEGER, " +
                rating + " INTEGER, " +
                review + " TEXT, " +
                "FOREIGN KEY(movie_id) REFERENCES " + MOVIE_TABLE + "(_id), " + //reference to the movie in our db
                "FOREIGN KEY(user_id) REFERENCES " + USER_TABLE + "(_id)) "); //reference to user);
    }

    //automatically update when we make change to the User table
    //method will be called whenever we insert or update the table
    //When the table is upgraded, its version will increase.
    //By default it is 1;
    @Override
    public void onUpgrade(SQLiteDatabase database, int oldV, int newV) {
        database.execSQL("DROP TABLE IF EXISTS " + USER_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + MOVIE_TABLE);
        database.execSQL("DROP TABLE IF EXISTS " + RATINGS_TABLE);
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
        return data.rawQuery("Select * from " + USER_TABLE + " where " + username + "=?", new String[]{name });
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
        String[] selectArgs = new String[]{username};
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

    //TODO make this protected when you make a Rating Object in the model
    public void addRating(User user, String movie_title, String movie_date,
                             String movie_type, int movie_rating, String movie_review) {
        long u_id = -1; //user_id
        long m_id = -1; //movie_id

        SQLiteDatabase db = this.getReadableDatabase();
        String movieSelectQuery = String.format("SELECT _id FROM %s WHERE %s = ? AND %s = %s AND %s = ?",
                MOVIE_TABLE, title, date, movie_date, type);
        Cursor cursor = db.rawQuery(movieSelectQuery, new String[]{movie_title, movie_type});
        //TODO maybe a better way to do this
        while (!cursor.moveToFirst()) { //movie doesnt exist in database
            ContentValues columnIndex = new ContentValues(); //add new movie
            columnIndex.put(title, movie_title);
            columnIndex.put(date, movie_date);
            columnIndex.put(type, movie_type);
            db.insert(MOVIE_TABLE, null, columnIndex);
            //deprecated
            cursor.requery();
        }
        m_id = cursor.getInt(0);
        cursor.close();
        String userSelectQuery = String.format("SELECT _id FROM %s WHERE %s = ?",
                USER_TABLE, username);
        cursor = db.rawQuery(userSelectQuery, new String[]{user.getUsername()});

        if(cursor.moveToFirst()) {
            u_id = cursor.getInt(0);
        } else {
            throw new IllegalArgumentException("user is not valid");
        }
        cursor.close();
        ContentValues columnIndex = new ContentValues(); //add rating
        columnIndex.put("movie_id", m_id);
        columnIndex.put("user_id", u_id);
        columnIndex.put(rating, movie_rating);
        columnIndex.put(review, movie_review);
        db.insert(RATINGS_TABLE, null, columnIndex);
    }


}
