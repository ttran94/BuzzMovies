package com.example.taitran.buzzmovie.controller;

import android.app.Fragment;
import android.app.ListActivity;
import android.app.SearchManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SearchView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringWriter;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.net.URL;
import java.util.Iterator;
import android.widget.ListAdapter;
import android.widget.Toast;

import org.apache.commons.io.IOUtils;

/**
 * Created by andie on 2/24/2016.
 */
public class SearchActivity extends ListActivity {

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get the intent, verify the action and get the query
        ListView lView = ((ListView) findViewById(R.id.listView));
        Intent intent = getIntent();
        String query = intent.getStringExtra(SearchManager.QUERY);
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
            CharSequence text = e.getMessage();
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast message = Toast.makeText(context, text, duration);
            message.show();
        }

        //((Button) findViewById(R.id.okbutton)).setVisibility(View.VISIBLE);
    }

    /**
     * parses the json object into an array of strings with the
     * title, year, and type given for each string in the array.
     * @param json
     * @return String array (results of the query)
     * @throws JSONException
     */
    public String[] parseJson (JSONObject json) throws JSONException{
        Iterator<String> keys = json.keys();
        String info = "";
        String[] results = new String[10];
        int i = 0;
        while (keys.hasNext() && i < 10) {
            JSONObject record = new JSONObject(json.get(keys.next()).toString());
            info += record.getString("Title");
            info += " | " + record.getString("Year");
            info += " | " + record.getString("Type");
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
        Log.d("Search Activity", "Opening stream to omdb");
        String url = "http://www.omdbapi.com/?s=" + query;
        InputStream stream = new URL(url).openStream();
        StringWriter writer = new StringWriter();
        IOUtils.copy(stream, writer);
        stream.close();
        Log.d("Search Activity", "stream successful");
        String jsonText = IOUtils.toString(stream, Charset.forName("UTF-8"));
        return new JSONObject(jsonText);
    }

    /*public void okButtonPressed(View v) {
        ListView lView = ((ListView) findViewById(R.id.listView));
        Intent intent = getIntent();
        String query = intent.getStringExtra(SearchManager.QUERY);
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
            CharSequence text = e.getMessage();
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast message = Toast.makeText(context, text, duration);
            message.show();
        }
    }*/
}
