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
    protected static final String bio = "bio";
    protected static  final String status = "status";
    protected static  final String user_type = "type";
    //User Type
    protected static  final String normal = "User";
    protected static  final String admin = "Admin";
    //STATUSES
    protected static  final String unlocked = "Unlocked";
    protected static  final String locked = "Locked";
    protected static  final String banned = "Banned";
    private static final String ACTIVE_USER = "Active_User";

    private static final String RATINGS_TABLE = "Ratings";
    private static final String score = "score";
    private static final String comment = "comment";
    private static final String user_major = "user_major";

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
                bio + " VARCHAR(255), " +
                status + " VARCHAR(255), " + //unlocked, locked, banned
                user_type + " VARCHAR(255))");

        database.execSQL("CREATE TABLE " + MOVIE_TABLE + " (" + //TODO maybe add genre/description
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                title + " VARCHAR(255), " +
                date + " INTEGER, " +
                type + " VARCHAR(255), poster TEXT)");

        database.execSQL("CREATE TABLE " + RATINGS_TABLE + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                "movie_id INTEGER, " +
                "user_id INTEGER, " +
                user_major + " TEXT, " + //add user_major to rating table. It makes life easier xD
                score + " REAL, " +
                comment + " TEXT, " +
                "FOREIGN KEY(movie_id) REFERENCES " + MOVIE_TABLE + "(_id), " + //reference to the movie in our db
                "FOREIGN KEY(user_id) REFERENCES " + USER_TABLE + "(_id)) "); //reference to user);
        database.execSQL("CREATE TABLE " + ACTIVE_USER + " (" +
                "_id INTEGER PRIMARY KEY AUTOINCREMENT, " +
                username + " VARCHAR(255), " +
                password + " VARCHAR(255), " +
                email + " VARCHAR(255), " +
                major + " VARCHAR(255), " +
                bio + " VARCHAR(255), " +
                status + " VARCHAR(255), " + //unlocked, locked, banned
                user_type + " VARCHAR(255))");
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


    /**
     * get the data if username exists
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
    protected void addUser(String username, String password, String email, String type) {
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues columnIndex = new ContentValues();
        columnIndex.put(this.username, username);
        columnIndex.put(this.password, password);
        columnIndex.put(this.email, email);
        columnIndex.put(this.major, "");
        columnIndex.put(this.bio, "");
        columnIndex.put(this.status, unlocked);
        if (type.equals(normal)) {
            columnIndex.put(this.user_type, normal);
        } else {
            columnIndex.put(this.user_type, admin);
        }
        data.insert(USER_TABLE, null, columnIndex);
    }

    /**
     * this method adds logged on user for data persistent
     * when the app is closed
     * @param user object which contains user's infomation
     * @param status of the user
     */
    protected void addActiveUser(User user, String status) {
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues columnIndex = new ContentValues();
        columnIndex.put(this.username, user.getUsername());
        columnIndex.put(this.password, user.getPassword());
        columnIndex.put(this.email, user.getEmail());
        columnIndex.put(this.major, user.getMajor());
        columnIndex.put(this.bio, user.getBio());
        columnIndex.put(this.status, status);
        if (user.getType().equals(normal)) {
            columnIndex.put(this.user_type, normal);
        } else {
            columnIndex.put(this.user_type, admin);
        }
        data.insert(ACTIVE_USER, null, columnIndex);
    }


    /**
     * check if the user is already logged in
     * if yes then set the active user to that user
     * @return true or false if the user is logged in or not
     */
    public boolean isUserLoggedIn() {
        boolean isLoggedIn = true;
        SQLiteDatabase data = this.getReadableDatabase();
        Cursor current = data.rawQuery("Select * from " + ACTIVE_USER, null);
        int value = current.getCount();
        if (value <= 0) {
            isLoggedIn = false;
        }
        current.close();
        return isLoggedIn;
    }

    public Cursor getUser() {
        SQLiteDatabase data = this.getReadableDatabase();
        return data.rawQuery("Select * from " + ACTIVE_USER , null);
    }

    /**
     * set the active user to null and log the user out of the system
     * @param user object that we want to remove and set to null
     */
    public void logUserOut(User user) {
        SQLiteDatabase data = this.getWritableDatabase();
        String whereClause = "username" + "=?";
        String[] whereArgs = new String[] { user.getUsername() };
        data.delete(ACTIVE_USER, whereClause, whereArgs);
        UserManagement clearUser = new UserManager();
        clearUser.logOut(null);
    }

    /**
     * update major info to server
     * @param major user's major
     * @param username user's name
     */
    protected void setMajor(String major, String username) {
        String[] selectArgs = new String[]{username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newMajor = new ContentValues();
        newMajor.put("major", major);
        data.update(USER_TABLE, newMajor, "username =?", selectArgs);
        data.update(ACTIVE_USER, newMajor, "username =?", selectArgs);
}


    /**
     * update update bio info to server
     * @param bio user's bio
     * @param username user's name
     */
    protected void setBio(String bio, String username) {
        String[] selectArgs = new String[] {username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newBio = new ContentValues();
        newBio.put("bio", bio);
        data.update(USER_TABLE, newBio, "username =?", selectArgs);
        data.update(ACTIVE_USER, newBio, "username =?", selectArgs);
    }

    /**
     * update password info to server
     * @param password user's major
     * @param username user's name
     */
    protected void setPassword(String password, String username) {
        String[] selectArgs = new String[] {username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newPassword = new ContentValues();
        newPassword.put("password", password);
        data.update(USER_TABLE, newPassword, "username =?", selectArgs);
        data.update(ACTIVE_USER, newPassword, "username =?", selectArgs);
    }

    /**
     * update email info to server
     * @param email user's email
     * @param username user's name
     */
    protected void setEmail(String email, String username) {
        String[] selectArgs = new String[] {username};
        SQLiteDatabase data = this.getWritableDatabase();
        ContentValues newEmail = new ContentValues();
        newEmail.put("email", email);
        data.update(USER_TABLE, newEmail, "username =?", selectArgs);
        data.update(ACTIVE_USER, newEmail, "username =?", selectArgs);
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
        String movieSelectQuery = String.format("SELECT _id FROM %s WHERE %s = ? AND %s = ?",
                MOVIE_TABLE, title, type);
        Cursor cursor = db.rawQuery(movieSelectQuery, new String[]{movie_title, movie_type});
        if (!cursor.moveToFirst()) { //movie doesnt exist in database
            ContentValues columnIndex = new ContentValues(); //add new movie
            columnIndex.put(title, movie_title);
            columnIndex.put(date, movie_date);
            columnIndex.put(type, movie_type);
            columnIndex.put(poster, movie_url);
            db.insert(MOVIE_TABLE, null, columnIndex);
        }
        cursor.close();
        Cursor cursor1 = db.rawQuery(movieSelectQuery, new String[]{movie_title, movie_type});
        cursor1.moveToFirst();
        m_id = cursor1.getInt(cursor1.getColumnIndex("_id"));
        String userSelectQuery = String.format("SELECT * FROM %s WHERE %s = ?",
                USER_TABLE, username);
        Cursor userTable = db.rawQuery(userSelectQuery, new String[]{rating.getUsername()});
        if(userTable.moveToFirst()) {
            u_id = Long.parseLong(userTable.getString(userTable.getColumnIndex("_id")));
            major = userTable.getString(userTable.getColumnIndex("major"));
        } else {
            throw new IllegalArgumentException("user is not valid");
        }
        userTable.close();
        //check if the movie is already rated by that same user
        //if yes, then update the score
        //otherwise, add it to the database.
        Cursor check = db.rawQuery("SELECT user_id FROM Ratings WHERE user_id = ? AND movie_id = ?", new String[] {String.valueOf(u_id), String.valueOf(m_id)});
        boolean exist = check.moveToFirst();
        ContentValues columnIndex = new ContentValues(); //add rating
        columnIndex.put("movie_id", m_id);
        columnIndex.put("user_id", u_id);
        columnIndex.put(score, movie_score);
        columnIndex.put(comment, movie_comment);
        columnIndex.put(user_major, major);
        if (exist) {
            String[] selectArgs = new String[] {String.valueOf(u_id), String.valueOf(m_id)};
            SQLiteDatabase data = this.getWritableDatabase();
            ContentValues update = new ContentValues();
            update.put(user_major, major);
            update.put(score, rating.getScore());
            update.put(comment, rating.getComment());
            data.update(RATINGS_TABLE, update, "user_id =? AND movie_id = ?", selectArgs);
        } else {
            db.insert(RATINGS_TABLE, null, columnIndex);
        }
        check.close();
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
        Cursor ratingList = db.rawQuery("SELECT * FROM Ratings WHERE score >= ? AND score < ?+1", new String[] {rating, rating});
        if(major != "Default" && rating != "Default") {
            ratingList = db.rawQuery("SELECT * FROM Ratings WHERE score >= ? AND score < ?+1 AND user_major = ?", new String[]{rating, rating, major});
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

    /**
     * Queries the user table with some filter on the status of users to return
     * @param statusFilter search for users of a certain status
     *                     - default (all), unlocked, locked, banned
     * @return list of strings formatted as "username | email | status"
     */
    public ArrayList<String> getUserList(String statusFilter) {
        ArrayList<String> userList = new ArrayList<>();
        String userText = "";
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor users;
        if (statusFilter.equals("Default")) {
            users = db.rawQuery("SELECT "
                    + username + ", "
                    + email + ", "
                    + status + " "
                    + "FROM " + USER_TABLE, null);
        } else {
            users = db.rawQuery("SELECT "
                    + username + ", "
                    + email + ", "
                    + status
                    + " FROM " + USER_TABLE
                    + " WHERE " + status + " = ?", new String[]{statusFilter});
        }
        while (users.moveToNext()) {
            String username = users.getString(0);
            String email = users.getString(1);
            String status = users.getString(2);
            userText += username;
            userText += " | " + email;
            userText += " | " + status;
            userList.add(userText);
            userText = "";
        }
        users.close();
        return userList;
    }


    /**
     * Updates the status of a user in the db
     * useful for banning/unlocking users
     * @param username the user to change status for
     * @param status what status to set (Unlocked, Locked, Banned)
     */
    public void updateUserStatus(String username, String status) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues cv = new ContentValues();
        cv.put(this.status, status);
        db.update(USER_TABLE, cv, this.username + " = ?", new String[]{username});
    }


    public ArrayList<String> getRatings(Movie movie, String score, String major) {
        ArrayList<String> ratingList = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        String u_id;
        String m_id;
        Cursor movieCursor = db.rawQuery("SELECT _id FROM " + MOVIE_TABLE
                + " WHERE "
                + title + " = ? AND "
                + date + " = ? AND "
                + type + " = ?",
                new String[]{movie.getTitle(), movie.getYear(), movie.getType()});

        if (movieCursor.moveToFirst()) {
            m_id = movieCursor.getString(0);
        } else {
            return ratingList;
        }
        movieCursor.close();


        String query = "SELECT user_id, "
                + user_major + ", "
                + this.score + ", "
                + comment
                + " FROM " + RATINGS_TABLE
                + " WHERE movie_id = ?";
        String[] selectArgs;

        if (major.equals("Default") && score.equals("Default")) {
            selectArgs = new String[]{m_id};
        } else if (major.equals("Default")) {
            int scoreNumber = Integer.parseInt(score);
            query += " AND " + this.score
                    + " >= " + scoreNumber
                    + " AND " + this.score
                    + " <= " + (scoreNumber + 0.99); //in range
            selectArgs = new String[]{m_id};
        } else if (score.equals("Default")) {
            query += " AND " + user_major
                    + " = ?";
            selectArgs = new String[]{m_id, major};
        } else {
            int scoreNumber = Integer.parseInt(score);
            query += " AND " + this.score
                    + " >= " + scoreNumber
                    + " AND " + this.score
                    + " <= " + (scoreNumber + 0.99)
                    + " AND " + user_major
                    + " = ?";
            selectArgs = new String[]{m_id, major};
        }

        Cursor ratingCursor = db.rawQuery(query, selectArgs);
        String ratingText = "";

        while(ratingCursor.moveToNext()) {
            u_id = ratingCursor.getString(0);
            Cursor usernameCursor = db.rawQuery("SELECT " + username
                + " FROM " + USER_TABLE + " WHERE _id = ?", new String[]{u_id});
            if (usernameCursor.moveToFirst()) {
                ratingText += usernameCursor.getString(0);
            }
            usernameCursor.close();
            String majorText = ratingCursor.getString(1);
            if (!majorText.equals(""))
                ratingText += " | " + majorText; //user_major
            ratingText += "\n"; //score
            for (int i = 0; i < Float.parseFloat(ratingCursor.getString(2)); i++)
                ratingText += "\u2605"; //add stars
            ratingText += "\n" + ratingCursor.getString(3); //comment
            ratingList.add(ratingText);
            ratingText = "";
        }
        ratingCursor.close();

        return ratingList;
    }
}

