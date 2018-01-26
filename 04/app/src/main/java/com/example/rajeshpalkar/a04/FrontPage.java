package com.example.rajeshpalkar.a04;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;


public class FrontPage extends Fragment implements View.OnClickListener{

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;
    private onButtonClickedListener mListener;

    public FrontPage() {
        // Required empty public constructor
    }


    // TODO: Rename and change types and number of parameters
    public static FrontPage newInstance(int sectionNumber) {
        FrontPage fragment = new FrontPage();
        Bundle args = new Bundle();
        args.putInt(ARG_PARAM1, sectionNumber);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mListener = (onButtonClickedListener) getContext();
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }



    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.button:
                mListener.onButtonClicked();
                break;
            case R.id.button2:
                Intent myIntent = new Intent(v.getContext(), Task1Activity.class);
                FrontPage.this.startActivity(myIntent);
                break;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_front_page, container, false);

        Button aboutBtn = (Button) rootView.findViewById(R.id.button);
        aboutBtn.setOnClickListener(this);
        Button task1Btn = (Button) rootView.findViewById(R.id.button2);
        task1Btn.setOnClickListener(this);

        return rootView;
    }


    public interface onButtonClickedListener
    {
        public void onButtonClicked();
    }

}
