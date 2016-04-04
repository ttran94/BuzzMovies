package com.example.taitran.buzzmovie.controller;

import android.content.Context;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.content.Intent;
import android.widget.TextView;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.Database;
import com.example.taitran.buzzmovie.model.FragmentAdapter;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

public class Dashboard extends AppCompatActivity{

    private UserManagement userMan;
    private Toolbar mtoolbar;
    private FragmentAdapter myadapter;
    private ViewPager mpager;
    private TabLayout mTab;
    private Database db;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        db = new Database(this);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        userMan = new UserManager();
        myadapter = new FragmentAdapter(getSupportFragmentManager());
        // add the fragment object to the adapter and attached it to the activity
        myadapter.addFragment(new EditProfileActivity(), "Profile");
        myadapter.addFragment(new Recommendation(), "Recommendation");
        mtoolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mtoolbar);
        mpager = (ViewPager) findViewById(R.id.pager);
        mTab = (TabLayout) findViewById(R.id.tab_layout);
        mpager.setAdapter(myadapter);
        mTab.setupWithViewPager(mpager);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.logout, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.d_logout) {
            Intent welcomePage = new Intent(this, WelcomeActivity.class);
            welcomePage.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK);
            welcomePage.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(welcomePage);
            db.logUserOut(userMan.getActiveUser());
            CharSequence text = "Successfully Logged Out";
            Context context = getApplicationContext();
            int duration = Toast.LENGTH_SHORT;
            Toast message = Toast.makeText(context, text, duration);
            message.show();
        } else if (id == R.id.search_button) {
            Log.d("Dashboard Activity", "search button pressed");
            Intent search = new Intent(this, SearchActivity.class);
            startActivity(search);
        }
        return super.onOptionsItemSelected(item);
    }


    @Override
    public void onBackPressed() {
        Intent home = new Intent(Intent.ACTION_MAIN);
        home.addCategory(Intent.CATEGORY_HOME);
        home.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(home);
    }
}
