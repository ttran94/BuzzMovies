package com.example.taitran.buzzmovie.model;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import android.content.Context;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

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
    protected static final String mov_id = "movie_id";
    protected static final String user_ID = "user_id";
    protected static final String bio = "bio";

    private static final String RATINGS_TABLE = "Ratings";
    private static final String score = "score";
    private static final String comment = "comment";

    private static final String MOVIE_TABLE = "Movies";
    private static final String title = "title";
    private static final String date = "date";
    private static final String type = "type";
    private static final String poster = "poster";
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
                type + " VARCHAR(255), poster TEXT)");

        database.execSQL("CREATE TABLE " + RATINGS_TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "movie_id INTEGER, " +
                "user_id INTEGER, " +
                "user_major TEXT, " + //add user_major to rating table. It makes life easier xD
                score + " REAL, " +
                comment + " TEXT, " +
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

    /**
     * check if user name is empty
     * @param name username
     * @return true or false if empty
     */
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

    /**
     * return a sub table of database
     * @param name username
     * @return cursor of table
     */
    protected Cursor getUserData(String name) {
        SQLiteDatabase data = this.getReadableDatabase();
        return data.rawQuery("Select * from " + USER_TABLE + " where " + username + "=?", new String[]{name });
    }

    //insert data to the User table(register)

    /**
     * add user to the database
     * @param username name of user
     * @param password password ofr user
     * @param email email of user
     */
    protected void addUser(String username, String password, String email) {
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues columnIndex = new ContentValues();
        columnIndex.put(this.username, username);
        columnIndex.put(this.password, password);
        columnIndex.put(this.email, email);
        columnIndex.put(this.major, "");
        columnIndex.put(this.bio, "");
        data.insert(USER_TABLE, null, columnIndex);
    }

    //update major info to server

    /**
     * update user major
     * @param major user's major
     * @param username user's name
     */
    protected void setMajor(String major, String username) {
        String[] selectArgs = new String[]{username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newMajor = new ContentValues();
        newMajor.put("major", major);
        data.update("User", newMajor, "username =?", selectArgs);
    }

    //update bio info to server
    /**
     * update user major
     * @param bio user's bio
     * @param username user's name
     */
    protected void setBio(String bio, String username) {
        String[] selectArgs = new String[] {username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newBio = new ContentValues();
        newBio.put("bio", bio);
        data.update("User", newBio, "username =?", selectArgs);
    }

    //update password info to server
    /**
     * update user major
     * @param password user's major
     * @param username user's name
     */
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

    /**
     * add rating and username to database
     * @param rating rating object which contains user's rating information
     */
    public void addRating(Rating rating) {
        long u_id = -1; //user_id
        long m_id = -1; //movie_id
        String major = "";

        String movie_title = rating.getMovie().getTitle();
        String movie_date = rating.getMovie().getYear();
        String movie_type = rating.getMovie().getType();
        String movie_url = rating.getMovie().getPoster();
        float movie_score = rating.getScore();
        String movie_comment = rating.getComment();

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
            columnIndex.put(poster, movie_url);
            db.insert(MOVIE_TABLE, null, columnIndex);
            //deprecated
            cursor.requery();
        }
        m_id = cursor.getInt(0);
        cursor.close();
        String userSelectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                USER_TABLE, username);
        cursor = db.rawQuery(userSelectQuery, new String[]{rating.getUsername()});
        if(cursor.moveToFirst()) {
            u_id = Long.parseLong(cursor.getString(cursor.getColumnIndex("_id")));
            major = cursor.getString(cursor.getColumnIndex("major"));
        } else {
            throw new IllegalArgumentException("user is not valid");
        }
        cursor.close();
        //check if the movie is already rated by that same user
        //if yes, then update the score
        //otherwise, add it to the database.
        Cursor check = db.rawQuery("SELECT user_id FROM Ratings WHERE user_id = ? AND movie_id = ?", new String[] {String.valueOf(u_id), String.valueOf(m_id)});
        boolean exist = check.moveToFirst();
        check.close();
        ContentValues columnIndex = new ContentValues(); //add rating
        columnIndex.put("movie_id", m_id);
        columnIndex.put("user_id", u_id);
        columnIndex.put(this.score, movie_score);
        columnIndex.put(this.comment, movie_comment);
        columnIndex.put("user_major", major);
        if (exist) {
            String[] selectArgs = new String[] {String.valueOf(u_id), String.valueOf(m_id)};
            SQLiteDatabase data = this.getWritableDatabase();
            ContentValues update = new ContentValues();
            update.put("user_major", major);
            update.put("score", rating.getScore());
            update.put("comment", rating.getComment());
            data.update("Ratings", update, "user_id =? AND movie_id = ?", selectArgs);
        } else {
            db.insert(RATINGS_TABLE, null, columnIndex);
        }
    }

    /**
     * Filter the Movies table with major and rating.
     * @param major filter by major
     * @param rating filter by rating
     * @return a filtered Arraylist
     */
    public ArrayList<FilterList> getMovieList(String major, String rating) {
        ArrayList<FilterList> movieList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        //TODO Select score between range for float
        // Somehow the query (score BETWEEN + rating + " AND " + rating + "0.9") doesn't work
        // no clue, but you can try to fix :)
        Cursor ratingList = db.rawQuery("SELECT * FROM Ratings WHERE score = ?", new String[] {rating});
        if(major != "Default" && rating != "Default") {
            ratingList = db.rawQuery("SELECT * FROM Ratings WHERE score = ? AND user_major = ?", new String[]{rating, major});
        } else if (major.equals("Default") && rating.equals("Default")) {
            ratingList = db.rawQuery("SELECT * FROM Ratings", null);
        } else if (major != "Default" && rating == "Default") {
            ratingList = db.rawQuery("SELECT * FROM Ratings WHERE user_major = ?", new String[]{major});
        }
            ArrayList<String> exist = new ArrayList<>();
            while (ratingList.moveToNext()) {
                String movie_id = ratingList.getString(ratingList.getColumnIndex("movie_id"));
                String user_major = ratingList.getString(ratingList.getColumnIndex("user_major"));
                String score = ratingList.getString(ratingList.getColumnIndex("score"));
                Cursor avgScore = db.rawQuery("SELECT AVG(score) FROM Ratings WHERE movie_id = ?", new String[]{movie_id});
                Cursor getMovie = db.rawQuery("SELECT * FROM Movies WHERE _id = ?", new String[]{movie_id});
                if (!(exist.contains(movie_id))) {
                    if(major != "Default" && rating != "Default") {
                        avgScore = db.rawQuery("SELECT AVG(score) FROM Ratings WHERE movie_id = ? AND user_major = ?", new String[]{movie_id, user_major});
                    } else if (major.equals("Default") && rating.equals("Default")) {
                        avgScore = db.rawQuery("SELECT AVG(score) FROM Ratings WHERE movie_id = ?", new String[]{movie_id});
                    } else if (major != "Default" && rating == "Default") {
                        avgScore = db.rawQuery("SELECT AVG(score) FROM Ratings WHERE movie_id = ? AND user_major = ?", new String[]{movie_id, user_major});
                    }

                    if (avgScore.moveToFirst() && getMovie.moveToFirst()) {
                        String movie_title = getMovie.getString(getMovie.getColumnIndex("title"));
                        String movie_date = getMovie.getString(getMovie.getColumnIndex("date"));
                        String movie_type = getMovie.getString(getMovie.getColumnIndex("type"));
                        String movie_url = getMovie.getString(getMovie.getColumnIndex("poster"));
                        float avg = avgScore.getInt(0);
                        FilterList selectedMovie = new FilterList(movie_title, movie_date, movie_type, movie_url, avg);
                        movieList.add(selectedMovie);
                        exist.add(movie_id);
                    }
                    avgScore.close();
                    getMovie.close();
                }
            }
            ratingList.close();
        return movieList;
    }

}

