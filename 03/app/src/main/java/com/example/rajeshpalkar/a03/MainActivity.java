package com.example.rajeshpalkar.a03;

import android.app.Activity;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
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
                intent = new Intent(this,Task2Activity.class);
                startActivity(intent);
                return true;
            case R.id.action_choice2:
                intent = new Intent(this,Task3Activity.class);
                startActivity(intent);
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }
}
