package com.example.rajeshpalkar.a06;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.HashMap;
import java.util.Locale;


public class MyFragmentPagerAdapter extends FragmentPagerAdapter {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private String mParam1;
    private String mParam2;



    MovieData movieData = new MovieData();
    int count;


    public MyFragmentPagerAdapter(FragmentManager fm) {
        // Required empty public constructor
        super(fm);
      //  count= size;
    }

    public Fragment getItem(int position){
        Fragment recycler = new Fragment();
        switch (position)
        {
            case 0:
                recycler = FragmentRecyclerView.newInstance(R.id.fragmentrecyclerview);
                break;
            case 1:
                recycler = FragmentRecyclerView1.newInstance(R.id.fragmentrecyclerview);
                break;
            case 2:
                recycler= FragmentRecyclerView2.newInstance(R.id.fragmentrecyclerview);
                break;
            case 3:
                recycler = FragmentRecyclerView3.newInstance(R.id.fragmentrecyclerview);
        }
       return recycler;
        //return FragmentDetails.newInstance((HashMap<String,?>)movieData.getItem(position));
    }

    public int getCount() {return 4;}

    public CharSequence getPageTitle(int position)
    {
        String name=null;
        if(position==2)
        {
            name="Top Rated";
        }

        if(position==1)
        {
            name="Popular";
        }

        if(position==0)
        {
        name="Now Playing";
        }

        if(position==3)
        {
            name="Upcoming";
        }

        return name;
    }

}
