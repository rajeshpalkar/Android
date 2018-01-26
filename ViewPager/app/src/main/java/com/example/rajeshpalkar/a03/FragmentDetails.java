package com.example.rajeshpalkar.a03;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.provider.ContactsContract;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import java.util.HashMap;

//import android.app.Fragment;

public class FragmentDetails extends Fragment {

    HashMap movie;
    private int total =0;


    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;



    public FragmentDetails() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static FragmentDetails newInstance(HashMap<String,?> movie) {
        FragmentDetails fragment = new FragmentDetails();
        Bundle args = new Bundle();
        args.putSerializable(ARG_PARAM1, movie);
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

        if(savedInstanceState!=null)
        {
            total = savedInstanceState.getInt("Total");
        }


        MovieData m = new MovieData();
        movie = (HashMap<String,?>) getArguments().getSerializable(ARG_PARAM1);


    }

    public void onSavedInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putInt("Total",total);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View rootView = inflater.inflate(R.layout.fragment_details, container, false);

        String mv =  (String) movie.get("name");
        TextView t2 = (TextView) rootView.findViewById(R.id.textView7);
        t2.setText(mv);

        String dir =  (String) movie.get("director");
        TextView t = (TextView) rootView.findViewById(R.id.textView5);
        t.setText(dir);

        String cast =  (String) movie.get("stars");
        TextView t1 = (TextView) rootView.findViewById(R.id.textView6);
        t1.setText(cast);

        String des =  (String) movie.get("description");
        TextView t3 = (TextView) rootView.findViewById(R.id.textView8);
        t3.setText(" "+des);

        ImageView imageView = (ImageView)rootView.findViewById(R.id.imageView4);
        imageView.setImageResource((int) movie.get("image"));

        RatingBar rb = (RatingBar) rootView.findViewById(R.id.ratingBar);
        Double movieRating = (Double) movie.get("rating");
        Double halfMovieRating = movieRating/2;
        rb.setRating(halfMovieRating.floatValue());

        return rootView;
    }


}
