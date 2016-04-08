package com.example.taitran.buzzmovie.controller;

import android.content.Context;
import android.content.Intent;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.taitran.buzzmovie.model.Database;
import com.example.taitran.buzzmovie.model.FragmentAdapter;
import com.example.taitran.buzzmovie.model.UserManagement;
import com.example.taitran.buzzmovie.model.UserManager;

public class AdminActivity extends AppCompatActivity {

    /**
     * The database.
     */
    private Database db;
    /**
     * User management for the admin.
     */
    private UserManagement userMan;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_admin);
        db = new Database(this);
        userMan = new UserManager(this);
        FragmentAdapter myAdapter = new FragmentAdapter(getSupportFragmentManager());
        // add the fragment object to the adapter and attached it to the activity
        myAdapter.addFragment(new UsersActivity(), "Users");
        myAdapter.addFragment(new AddUser(), "Add Account");
        Toolbar mToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(mToolbar);
        ViewPager mPager = (ViewPager) findViewById(R.id.pager);
        TabLayout mTab = (TabLayout) findViewById(R.id.tab_layout);
        mPager.setAdapter(myAdapter);
        mTab.setupWithViewPager(mPager);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.fragmenu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.logout) {
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
        }
        return super.onOptionsItemSelected(item);
    }

}