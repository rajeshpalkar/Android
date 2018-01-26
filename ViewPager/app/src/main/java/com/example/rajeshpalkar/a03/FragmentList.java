package com.example.rajeshpalkar.a03;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.os.SystemClock;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import java.util.HashMap;

//import android.app.Fragment;


public class FragmentList extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private int a = 0;


    public FragmentList() {
        // Required empty public constructor
    }

    public static FragmentList newInstance() {
        FragmentList fragment = new FragmentList();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }


        if(savedInstanceState!=null)
        {
            a = savedInstanceState.getInt("Counter");
        }
    }

    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);

        outState.putInt("Counter",a);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             final Bundle savedInstanceState) {
        // Inflate the layout for this fragment

        View rootView = inflater.inflate(R.layout.fragment_list, container, false);

        final TextView movieCounter = (TextView) rootView.findViewById(R.id.counter);
        final Button next = (Button) rootView.findViewById(R.id.next);
        final Button prev = (Button) rootView.findViewById(R.id.prev);



        final onListItemSelectedListener mListListener;
        mListListener = (onListItemSelectedListener) getContext();



        next.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                MovieData movieData = new MovieData();
                if(a==29){}else{
                a = a+1;}
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(a);
                mListListener.onListItemSelected(a, movie);
                movieCounter.setText(String.valueOf(a));
            }
        });

        prev.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v)
            {
                MovieData movieData = new MovieData();
                if(a==0){}else{
                a=a-1;}
                HashMap<String,?> movie = (HashMap<String,?>) movieData.getItem(a);
                mListListener.onListItemSelected(a, movie);
                movieCounter.setText(String.valueOf(a));

            }
        });

        movieCounter.setText(String.valueOf(a));


        return rootView;
    }



    public interface onListItemSelectedListener{
        public void onListItemSelected(int position, HashMap<String,?>movie);
    }

}
