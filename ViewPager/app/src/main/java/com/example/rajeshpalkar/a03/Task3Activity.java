package com.example.rajeshpalkar.a03;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import java.util.HashMap;

public class Task3Activity extends AppCompatActivity implements FragmentList.onListItemSelectedListener{


    private boolean mTwoPane;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task3);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.main_container, new FragmentList())
                    .commit();
        }

        if(findViewById(R.id.detail_container)!=null)
        {
            mTwoPane = true;
        }
    }

    public void onListItemSelected(int position, HashMap<String,?> movie)
    {
          if(mTwoPane)
          {

              getSupportFragmentManager().beginTransaction()
                      .replace(R.id.detail_container,FragmentDetails.newInstance(movie))
                      .commit();


          }
          else
          {

              getSupportFragmentManager().beginTransaction()
                      .replace(R.id.main_container,FragmentDetails.newInstance(movie))
                      .addToBackStack(null)
                      .commit();
          }
    }
}
