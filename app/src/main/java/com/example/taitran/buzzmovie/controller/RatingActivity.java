package com.example.taitran.buzzmovie.controller;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.ImageLoader;
import com.example.taitran.buzzmovie.model.VolleySingleton;

/**
 * Created by andie on 3/6/2016.
 */
public class RatingActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rating);
        Log.d("Movie Display", "I am here");
        Bundle extras = getIntent().getExtras();
        String title = (String) extras.get("title");
        String date = (String) extras.get("date");
        String type = (String) extras.get("type");
        String poster_url = (String) extras.get("poster_url");

        ((TextView)findViewById(R.id.title)).setText(title);
        ((TextView)findViewById(R.id.date)).setText(date);
        ((TextView)findViewById(R.id.type)).setText(type);
        Log.d("Movie Display", "Something happened");
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
        String review = ((EditText) findViewById(R.id.review)).getText().toString();
        float rating = ((RatingBar)findViewById(R.id.ratingBar)).getRating();
        if (rating == 0) {
            Toast.makeText(getApplicationContext(), "Please select a rating.", Toast.LENGTH_SHORT);
        }
    }
}
