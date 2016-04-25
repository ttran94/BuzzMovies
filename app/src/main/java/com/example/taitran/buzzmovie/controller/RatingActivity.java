package com.example.taitran.buzzmovie.controller;

import android.content.Context;
import android.content.Intent;
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
import com.example.taitran.buzzmovie.model.Rating;
import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;
import com.example.taitran.buzzmovie.model.VolleySingleton;
import com.example.taitran.buzzmovie.model.myAdapter;


public class RatingActivity extends AppCompatActivity {
    /**
     * The movie
     */
    private Movie movie;
    /**
     * The position
     */
    private int position;
    /**
     * The user manager
     */
    private UserManagement userMan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);

        Bundle extras = getIntent().getExtras();
        position = (int)extras.get("position");
        movie = myAdapter.getMovieList().get(position);
        String title = movie.getTitle();
        String date = movie.getYear();
        String type = movie.getType();
        String poster_url = movie.getPoster();
        userMan = new UserManager(this);

        ((TextView)findViewById(R.id.title)).setText(title);
        ((TextView)findViewById(R.id.date)).setText(date);
        ((TextView)findViewById(R.id.type)).setText(type);
        //TODO put this in a method somewhere to avoid code re-use
        if(poster_url != null && !"N/A".equals(poster_url)) {
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

    /**
     * store the rating of movies in database
     * along with the username whose rated that movie.
     * @param v reference to submit button when pressed
     */
    public void submitButtonPressed(View v) {
          //only need this for the active user
        Rating rating = new Rating(userMan.getActiveUser().getUsername(), movie,
                ((RatingBar)findViewById(R.id.ratingBar)).getRating(),
                ((EditText) findViewById(R.id.review)).getText().toString());
        Toast message;
        if (rating.getScore() == 0) {
            message = Toast.makeText(getApplicationContext(), "Please select a rating.", Toast.LENGTH_SHORT);
        } else {
            userMan.setRating(rating);
            message = Toast.makeText(getApplicationContext(), "Success", Toast.LENGTH_SHORT);
        }
        message.show();
    }

    /**
     * Review button pressed
     * @param v reference to the review button being pressed
     */
    public void reviewsButtonPressed(View v) {
        Intent reviews = new Intent(this, ReviewListActivity.class);
        //send position of the movie in movieList for access in the activity
        reviews.putExtra("position", position);
        startActivity(reviews);
        this.overridePendingTransition(R.anim.slide_in_up, R.anim.no_anim);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        this.overridePendingTransition(R.anim.no_anim, R.anim.slide_out_down);
    }
}
