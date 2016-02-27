package com.example.taitran.buzzmovie.controller;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.StrictMode;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import org.json.JSONException;
import org.json.JSONObject;
import org.json.JSONArray;
import java.io.IOException;
import java.io.InputStream;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.net.URL;
import java.util.Iterator;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

/**
 * Created by andie on 2/24/2016.
 */


public class SearchActivity extends AppCompatActivity {

    private EditText searchEditText;
    private ListView lView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);
        //TODO DONT DO THIS
        StrictMode.ThreadPolicy policy = new StrictMode.ThreadPolicy.Builder().permitAll().build();
        StrictMode.setThreadPolicy(policy);
        // Get the intent, verify the action and get the query
        lView = ((ListView) findViewById(R.id.listView));
        searchEditText = (EditText) findViewById(R.id.searchBar);
    }

    /**
     * parses the json object into an array of strings with the
     * title, year, and type given for each string in the array.
     * @param json
     * @return String array (results of the query)
     * @throws JSONException
     */
    public String[] parseJson (JSONObject json) throws JSONException{
        JSONArray jsonArray = json.getJSONArray("Search");
        String info;
        String[] results = new String[10];
        int i = 0;
        while (i < 10) {
            info = jsonArray.getJSONObject(i).getString("Title");
            info += " | " + jsonArray.getJSONObject(i).getString("Year");
            info += " | " + jsonArray.getJSONObject(i).getString("Type");
            results[i] = info;
            i++;
        }
        return results;
    }

    /**
     * Searches for the query in OMDB and returns a json object
     * @param query
     * @return JSON object with all movie results
     * @throws IOException if problem with stream
     * @throws JSONException if problem with json result
     */
    public JSONObject getjsonResults (String query) throws IOException, JSONException{
        //TODO learn some regex
        query = query.replaceAll("\\s+", "%20");
        Log.d("Search Activity", "Modified query: " + query);
        Log.d("Search Activity", "Opening stream to omdb");
        String url = "http://www.omdbapi.com/?s=" + query;
        //TODO don't do this on the main thread
        InputStream stream = new URL(url).openStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(stream, writer); //Charset.forName("UTF-8"));
        String jsonText = writer.toString();
        stream.close();
        writer.close();
        Log.d("Search Activity", "stream successful");
        return new JSONObject(jsonText);
    }

    public void goButtonPressed(View v) throws IOException, JSONException{
        String query = searchEditText.getText().toString();
        Log.d("Search Activity", "Search for " + query);
        try {
            JSONObject json = getjsonResults(query);
            //get title, year, etc. to display in search
            String [] results = parseJson(json);
            ListAdapter adapter = new ArrayAdapter<String>(this, android.R.layout.simple_selectable_list_item, results);
            lView.setVisibility(View.VISIBLE);
            lView.setAdapter(adapter);
            //lView.setOnItemSelectedListener(this);
        } catch (Exception e) {
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            //Have you tried turning it off and on again?
            Toast message = Toast.makeText(context, "No results", duration);
            message.show();
        }
    }

    public void clearButtonPressed(View v) {
        Log.d("Search Activity", "cancel button pressed");
        lView.setVisibility(View.GONE);
        lView.setAdapter(null);
        //Intent dash = new Intent(this, Dashboard.class);
        //startActivity(dash);
    }
}
