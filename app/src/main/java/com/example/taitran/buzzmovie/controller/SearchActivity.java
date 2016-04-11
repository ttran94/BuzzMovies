package com.example.taitran.buzzmovie.controller;

import android.app.DialogFragment;
import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;

import java.util.ArrayList;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.example.taitran.buzzmovie.model.Movie;
import com.example.taitran.buzzmovie.model.VolleySingleton;
import com.example.taitran.buzzmovie.model.myAdapter;

public class SearchActivity extends AppCompatActivity {

    private static final String SAVED_MOVIE ="movies" ;
    private RequestQueue queue;
    private ArrayList<Movie> movieList;
    private myAdapter myadapter;
    private RecyclerView viewList;
    private EditText searchEditText;
    /**
     * 0 : all
     * 1 : movies
     * 2 : series
     * 3 : episode
     */
    public static int searchType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        VolleySingleton volleySingleton = VolleySingleton.getInstance(this);
        queue = volleySingleton.getRequest();
        movieList = new ArrayList<>();
        viewList = ((RecyclerView) findViewById(R.id.myList));
        searchEditText = (EditText) findViewById(R.id.searchBar);
        myadapter = new myAdapter(this);
        viewList.setLayoutManager(new LinearLayoutManager(this));
        if (savedInstanceState != null) {
            movieList = savedInstanceState.getParcelableArrayList(SAVED_MOVIE);
            myadapter.setMovieList(movieList);
        }
        viewList.setAdapter(myadapter);
    }
    @Override
    protected void onSaveInstanceState(Bundle bundle) {
        super.onSaveInstanceState(bundle);
        bundle.putParcelableArrayList(SAVED_MOVIE, movieList);
    }
    /**
     * Send a REST request and expect a JSON response
     * in this case, we will get the JSONObject that contains the search result
     * @param v reference to the pressed button
     */
    public void goButtonPressed(View v) {
        String url = "http://www.omdbapi.com/?s=";
        String query = searchEditText.getText().toString();
        //replace all white spaces with "%20", "+" also work
        query = query.replaceAll("\\s+", "%20");
        switch(searchType) {
            case 0: break;
            case 1: query += "&type=movie";
                break;
            case 2: query += "&type=series";
                break;
            case 3: query += "&type=episode";
                break;
        }

        JsonObjectRequest request = new JsonObjectRequest(Request.Method.GET,
                url + query,
                (String) null,
                new Response.Listener<JSONObject>() {
                    @Override
                    //this is called if the response comes back from the server
                    public void onResponse(JSONObject response) {
                        //clear the movie list for each request;
                        movieList.clear();
                        //once we have the data then send it parseJson to process the info
                        movieList = parseJson(response);
                        //set the movie list for adapter, so that it can passed the info to the layout.
                        myadapter.setMovieList(movieList);
                        //set a new recycler list for every request
                        viewList.setAdapter(myadapter);
                    }
                }, new Response.ErrorListener() {
            // this is called if the server doesn't response, response = false
            @Override
            public void onErrorResponse(VolleyError error) {
                Context context = getApplicationContext();
                CharSequence text = "No Movie Found";
                int duration = Toast.LENGTH_SHORT;
                Toast message = Toast.makeText(context, text, duration);
                message.show();
            }
        });
        queue.add(request);
    }

    /**
     * return an array list that contains movie objects
     * the object has the information for each movie
     * @param response take in a JSONObject then process its info
     * @return an array list of movies
     */
    private ArrayList<Movie> parseJson(JSONObject response) {
        CharSequence text;
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_SHORT;
        if (response == null || response.length() == 0) {
            text = "No movie found";
            Toast message = Toast.makeText(context, text, duration);
            message.show();
        } else {
            //get the JSONArray from the JSONObject then assign its info to movie.
            try {
                JSONArray movieArray = response.getJSONArray("Search");
                for (int i = 0; i < movieArray.length(); i++) {
                    JSONObject movie = movieArray.getJSONObject(i);
                    String title = movie.getString("Title");
                    String year = movie.getString("Year");
                    String type = movie.getString("Type");
                    String poster = movie.getString("Poster");
                    Movie info = new Movie(title, year, type, poster);
                    movieList.add(info);
                }
            } catch (JSONException e) {
                text = "No Movie Found";
                Toast message = Toast.makeText(context, text, duration);
                message.show();
            }
        }
        return movieList;
    }

    /**
     * clear the text field and recycler view
     * @param v reference to the pressed button
     */
    public void clearButtonPressed(View v) {
        Log.d("Search Activity", "cancel button pressed");
        searchEditText.setText("");
        movieList.clear();
        myadapter.setMovieList(movieList);
        viewList.setAdapter(myadapter);
    }

    public void settingsButtonPressed(View v) {
        DialogFragment menuDialog = new MenuDialog();
        menuDialog.show(getFragmentManager().beginTransaction(), "Search Settings");
    }

}
