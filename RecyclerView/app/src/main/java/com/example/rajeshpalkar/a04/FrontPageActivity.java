package com.example.rajeshpalkar.a04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class FrontPageActivity extends AppCompatActivity implements FrontPage.onButtonClickedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_front_page);


        if(savedInstanceState == null) {
            getFragmentManager().beginTransaction()
                    .replace(R.id.container, FrontPage.newInstance(R.id.frontfragment))
                    .commit();
        }
        else {

        }
    }


    public void onButtonClicked()
    {
        getFragmentManager()
                .beginTransaction().addToBackStack(null)
                .replace(R.id.container, new AboutMe())
                .commit();
    }
}
