package com.example.rajeshpalkar.homework2;

import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.content.Intent;
import android.view.View;
import android.widget.Button;
import android.widget.RelativeLayout;

public class MainActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button aboutBtn = (Button) findViewById(R.id.Task2Button);
        aboutBtn.setOnClickListener(this);
        Button task2Btn = (Button) findViewById(R.id.Task3Button);
        task2Btn.setOnClickListener(this);
        Button task3Btn = (Button) findViewById(R.id.Task4Button);
        task3Btn.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu (Menu menu){
        getMenuInflater().inflate(R.menu.menu_main,menu);
        return true;
    }

    public void onClick(View v) {
        Intent intent;
        switch (v.getId()) {
            case R.id.Task2Button:
                Intent myIntent = new Intent(v.getContext(), Activity_LinearLayout.class);
                startActivity(myIntent);
                break;
            case R.id.Task3Button:
                Intent myIntent2 = new Intent(v.getContext(), Activity_GridLayout.class);
                startActivity(myIntent2);
                break;
            case R.id.Task4Button:
                Intent myIntent1 = new Intent(v.getContext(), Activity_ControlsAndEvents.class);
                startActivity(myIntent1);
                break;

        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item){
        int id = item.getItemId();
        Intent intent;

        switch(id){
            case R.id.action_choice1:
                intent = new Intent(this,Activity_LinearLayout.class);
                startActivity(intent);
                return true;
            case R.id.action_choice3:
                intent = new Intent(this,Activity_GridLayout.class);
                startActivity(intent);
                return true;
            case R.id.action_choice4:
                intent = new Intent(this,Activity_ControlsAndEvents.class);
                startActivity(intent);
                return true;
            default:
                return onOptionsItemSelected(item);
        }
    }


}
