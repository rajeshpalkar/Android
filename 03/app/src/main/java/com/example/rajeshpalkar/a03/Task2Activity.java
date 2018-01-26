package com.example.rajeshpalkar.a03;

import android.app.Fragment;
import android.app.FragmentManager;
import android.net.Uri;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.HashMap;
import java.util.Locale;

public class Task2Activity extends AppCompatActivity {

    MyFragmentPagerAdapter myPagerAdapter;
    ViewPager mViewPager;
    MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        movieData = new MovieData();

        myPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager(), movieData.getSize());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.setCurrentItem(3);
        customizeViewPager();


        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

    }


    public void customizeViewPager() {

                mViewPager.setPageTransformer(false,new ViewPager.PageTransformer(){

                    public void transformPage(View page, float position){
                        final float normalized_position = Math.abs(Math.abs(position)-1);
                        page.setScaleX(normalized_position/2+ 0.5f);
                        page.setScaleY(normalized_position/2+ 0.5f);

                        page.setRotationY(position * -50);
                    }

                });
    }

}


/*

    public class MyFragmentStatePagerAdapter extends FragmentStatePagerAdapter {

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
        private String mParam1;
        private String mParam2;

        MovieData movieData = new MovieData();
        int count;


        public MyFragmentStatePagerAdapter(FragmentManager fm, int size) {
            // Required empty public constructor
            super(fm);
            count = size;
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

        public static MyFragmentPagerAdapter newInstance(String param1, String param2) {
            MyFragmentPagerAdapter fragment = new MyFragmentPagerAdapter();
            Bundle args = new Bundle();
            args.putString(ARG_PARAM1, param1);
            args.putString(ARG_PARAM2, param2);
            fragment.setArguments(args);
            return fragment;
        }

        @Override
        public void onCreate(Bundle savedInstanceState) {
            super.onCreate(savedInstanceState);
            if (getArguments() != null) {
                mParam1 = getArguments().getString(ARG_PARAM1);
                mParam2 = getArguments().getString(ARG_PARAM2);
            }
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            // Inflate the layout for this fragment
            return inflater.inflate(R.layout.fragment_pager_adapter, container, false);
        }



    }
}
*/
