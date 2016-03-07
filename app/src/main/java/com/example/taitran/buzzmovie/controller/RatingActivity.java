package com.example.taitran.buzzmovie.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.taitran.buzzmovie.Rating;
import com.example.taitran.buzzmovie.model.Database;
import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;
import com.example.taitran.buzzmovie.model.VolleySingleton;
import com.example.taitran.buzzmovie.model.myAdapter;

/**
 * Created by andie on 3/6/2016.
 */
public class RatingActivity extends AppCompatActivity {
    private Movie movie;
    private String title;
    private String date;
    private String type;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Bundle extras = getIntent().getExtras();
        movie = myAdapter.getMovieList().get((int) extras.get("position"));
        title = movie.getTitle();
        date = movie.getYear();
        type = movie.getType();
        String poster_url = movie.getPoster();

        ((TextView)findViewById(R.id.title)).setText(title);
        ((TextView)findViewById(R.id.date)).setText(date);
        ((TextView)findViewById(R.id.type)).setText(type);
        //TODO put this in a method somewhere to avoid code re-use
        if(poster_url != null && !poster_url.equals("N/A")) {
            VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
            volleySingleton.getImage().get(poster_url, new ImageLoader.ImageListener() {
                @Override
                public void onResponse(ImageLoader.ImageContainer response, boolean isImmediate) {
                    ((ImageView) findViewById(R.id.poster)).setImageBitmap(response.getBitmap());
                }

                @Override
                public void onErrorResponse(VolleyError error) {
                }
            });
        }
    }

    public void submitButtonPressed(View v) {
        UserManagement userMan = new UserManager(this); //only need this for the active user
        Rating rating = new Rating(userMan.getActiveUser().getUsername(), movie,
                ((RatingBar)findViewById(R.id.ratingBar)).getNumStars(),
                ((EditText) findViewById(R.id.review)).getText().toString());
        Toast message;
        if (rating.getScore() == 0) {
            message = Toast.makeText(getApplicationContext(), "Please select a rating.", Toast.LENGTH_SHORT);
        } else {

            //TODO make active user a singleton
            Database db = new Database(this);
            db.addRating(rating);
            message = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
        }
        message.show();
    }
}
