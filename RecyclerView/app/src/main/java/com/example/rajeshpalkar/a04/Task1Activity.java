package com.example.rajeshpalkar.a04;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import java.util.HashMap;

public class Task1Activity extends AppCompatActivity implements FragmentRecyclerView.OnListItemSelectedListener{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task1);


        if(savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, FragmentRecyclerView.newInstance(R.id.fragmentrecyclerview))
                    .commit();
        }
        else {

        }
    }

    public void onListItemSelected(int position, HashMap<String,?>movie)
    {
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.main_container,FragmentDetails.newInstance(movie))
                .addToBackStack(null)
                .commit();

    }
}
