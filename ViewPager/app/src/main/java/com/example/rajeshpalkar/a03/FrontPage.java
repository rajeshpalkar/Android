package com.example.rajeshpalkar.a03;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;


public class FrontPage extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "section_number";
    private String mParam1;
    private onButtonClickedListener mListener;


    public FrontPage() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = (onButtonClickedListener) getContext();

        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
        }
    }


    public static FrontPage newInstance(int sectionNumber) {
        FrontPage fragment = new FrontPage();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button:
                mListener.onButtonClicked();
                break;
            case R.id.button2:
                Intent myIntent = new Intent(v.getContext(), Task2Activity.class);
                FrontPage.this.startActivity(myIntent);
                break;
            case R.id.button3:
                Intent myIntent1 = new Intent(v.getContext(), Task3Activity.class);
                FrontPage.this.startActivity(myIntent1);
                break;

        }
    }


    @Override
    public View onCreateView(final LayoutInflater inflater, final ViewGroup container,
                             Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_front_page, container, false);

        Button aboutBtn = (Button) rootView.findViewById(R.id.button);
        aboutBtn.setOnClickListener(this);
        Button task2Btn = (Button) rootView.findViewById(R.id.button2);
        task2Btn.setOnClickListener(this);
        Button task3Btn = (Button) rootView.findViewById(R.id.button3);
        task3Btn.setOnClickListener(this);

        return rootView;
    }


    public interface onButtonClickedListener
    {
        public void onButtonClicked();
    }





}
