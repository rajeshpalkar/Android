package com.example.rajeshpalkar.a08;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;

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
    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }


    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.action_choice0:
                intent = new Intent(this,FrontPageActivity.class);
                startActivity(intent);
                return true;
            case R.id.action_choice1:
                intent = new Intent(this,Task1Activity.class);
                startActivity(intent);
                return true;
            default:
                return onOptionsItemSelected(item);
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
