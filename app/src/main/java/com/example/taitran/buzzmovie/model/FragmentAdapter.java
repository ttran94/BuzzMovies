package com.example.taitran.buzzmovie.model;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class FragmentAdapter extends FragmentStatePagerAdapter{
    /**
     * List of fragments
     */
    private final List<Fragment> list = new ArrayList<>();
    /**
     * List of titles
     */
    private final List<String> titleList = new ArrayList<>();

    /**
     * The fragment adapter
     * @param fm the fragment manager
     */
    public FragmentAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return list.get(position);
    }

    @Override
    public int getCount() {
        return list.size();
    }

    /**
     * Add a fragment
     * @param fragment the fragment to add
     * @param title the title of the fragment
     */
    public void addFragment(Fragment fragment, String title) {
        list.add(fragment);
        titleList.add(title);
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titleList.get(position);
    }
}
