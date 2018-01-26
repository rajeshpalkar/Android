package com.example.rajeshpalkar.a06;

import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.View;

import java.util.HashMap;

public class AsyncAcitivity extends AppCompatActivity implements FragmentRecyclerView.OnListItemSelectedListener,
FragmentRecyclerView1.OnListItemSelectedListener,
FragmentRecyclerView2.OnListItemSelectedListener,
FragmentRecyclerView3.OnListItemSelectedListener{

    MyFragmentPagerAdapter myPagerAdapter;
    ViewPager mViewPager;
    MovieData movieData;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_async);

        movieData = new MovieData();

        myPagerAdapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        mViewPager = (ViewPager) findViewById(R.id.pager);
        mViewPager.setAdapter(myPagerAdapter);
        customizeViewPager();

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(mViewPager);

        if (savedInstanceState == null) {

            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, FragmentRecyclerView.newInstance(R.id.fragmentrecyclerview))
                    .commit();
        } else {

        }

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



    public void onListItemSelected(int position, HashMap<String,?> movie)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.pagercontainer,FragmentDetails.newInstance(movie))
                .addToBackStack(null)
                .commit();



    }
}
