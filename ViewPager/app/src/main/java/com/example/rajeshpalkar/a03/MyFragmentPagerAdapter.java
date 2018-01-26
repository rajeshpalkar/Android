package com.example.rajeshpalkar.a03;

import android.support.v4.app.FragmentManager;
import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Locale;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;

    MovieData movieData = new MovieData();
    int count;


    public MyFragmentPagerAdapter(FragmentManager fm, int size) {
        // Required empty public constructor
        super(fm);
        count= size;
    }

    public Fragment getItem(int position){
        return FragmentDetails.newInstance((HashMap<String,?>)movieData.getItem(position));
    }

    public int getCount() {return count;}

    public CharSequence getPageTitle(int position)
    {
        Locale l = Locale.getDefault();
        HashMap<String,?> movie = (HashMap<String,?>)movieData.getItem(position);
        String name = (String) movie.get("name");
        return name.toUpperCase(l);
    }


}
