package com.example.rajeshpalkar.a08;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.HashMap;
import java.util.Locale;

public class Task2Activity extends AppCompatActivity {
    private int[] layouts;
    MyFragmentPagerAdapter myPagerAdapter;
    ViewPager mViewPager;
    MovieData movieData;
    private IntroManager introManager;
    private TextView dots[];
    private LinearLayout dotsLayout;
    private Button skipBtn, prevBtn, nextBtn, doneBtn;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task2);

        movieData = new MovieData();
        introManager = new IntroManager(this);
        if(!introManager.isFirstTimeLaunch())
        {
            introManager.setFirstTimeLaunch(false);
            Intent intent = new Intent(Task2Activity.this,FrontPageActivity.class);
            startActivity(intent);
            finish();
        }

        myPagerAdapter = new MyFragmentPagerAdapter();
        mViewPager = (ViewPager) findViewById(R.id.pager);
        dotsLayout = (LinearLayout) findViewById(R.id.layoutDots);
        mViewPager.setAdapter(myPagerAdapter);
        mViewPager.addOnPageChangeListener(viewPageChangeListener);
       // mViewPager.setCurrentItem(3);

        layouts = new int[]{(R.layout.fragment_details1),
                (R.layout.fragment_details2),
                (R.layout.fragment_details3)};

      //  customizeViewPager();
        addBottomDots(0);
        changeStatusBarColor();

        if (Build.VERSION.SDK_INT >= 21) {
            getWindow().getDecorView().setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN);
        }

        skipBtn = (Button) findViewById(R.id.Skip);
        prevBtn = (Button) findViewById(R.id.Prev);
        nextBtn = (Button) findViewById(R.id.Next);
        doneBtn = (Button) findViewById(R.id.Done);

        skipBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Task2Activity.this, FrontPageActivity.class));
                finish();
            }
        });

        prevBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                int position = currenPosition(-1);
                if (position >= 0)
                    mViewPager.setCurrentItem(position);

            }
        });
        nextBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int position = currenPosition(1);
                if (position < movieData.getSize())
                    mViewPager.setCurrentItem(position);
            }
        });

        doneBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Task2Activity.this, FrontPageActivity.class));
                finish();
            }
        });


    }

        ViewPager.OnPageChangeListener viewPageChangeListener = new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
            addBottomDots(position);
                if (position == movieData.getSize() - 1) {
                    nextBtn.setText(getString(R.string.start));
                    skipBtn.setVisibility(View.GONE);
                } else {
                    nextBtn.setText(getString(R.string.next));
                    skipBtn.setVisibility(View.VISIBLE);
                }

            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        };

        private void changeStatusBarColor() {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                window.setStatusBarColor(Color.TRANSPARENT);
            }
        }





    private void addBottomDots(int position)
    {
        dots = new TextView[layouts.length];
        int[] colorActive = getResources().getIntArray(R.array.dot_active);
        int[] colorInActive = getResources().getIntArray(R.array.dot_inactive);
        dotsLayout.removeAllViews();
        for(int i=0;i<dots.length;i++)
        {
            dots[i] = new TextView(this);
            dots[i].setText(Html.fromHtml("&#8226;"));
            dots[i].setTextSize(35);
            dots[i].setTextColor(colorInActive[position%3]);
            dotsLayout.addView(dots[i]);
        }

        if (dots.length > 0)
            dots[position%3].setTextColor(colorActive[position%3]);
    }



    public int currenPosition(int position)
    {
        return  mViewPager.getCurrentItem() + position;
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



    public class MyFragmentPagerAdapter extends PagerAdapter {

        private static final String ARG_PARAM1 = "param1";
        private static final String ARG_PARAM2 = "param2";
        private String mParam1;
        private String mParam2;

        public MyFragmentPagerAdapter(){

        }

        TextView mName;
        ImageView mImage;
        HashMap<String,?> movie;
        private LayoutInflater layoutInflater;

        public Object instantiateItem(ViewGroup container, int position)
        {
            layoutInflater = (LayoutInflater)getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View v = layoutInflater.inflate(layouts[position%3],container,false);



            mName = (TextView) v.findViewById(R.id.textView7);
            mImage = (ImageView) v.findViewById(R.id.imageView4);

            movieData = new MovieData();
            movie = movieData.getItem(position);

            mName.setText((String)movie.get("name"));

            if(movie.get("image")!=null)
            {
                mImage.setImageResource((Integer)movie.get("image"));
                mImage.animate().x(100).y(200).rotationYBy(380);


            }

            container.addView(v);

            return v;
        }

        public int getCount(){
            return movieData.getSize();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            View v = (View) object;
            container.removeView(v);
        }

    }

}


