package com.example.rajeshpalkar.homework2;

import android.content.res.Resources;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.IdRes;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GestureDetectorCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.GestureDetector;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.SeekBar;
import android.widget.TextView;
import android.widget.Toast;

import org.w3c.dom.Text;

import java.util.HashMap;
import java.util.List;

import static com.example.rajeshpalkar.homework2.R.id.image;
import static com.example.rajeshpalkar.homework2.R.id.seekBar;

public class Activity_ControlsAndEvents extends AppCompatActivity implements View.OnClickListener{

    private GestureDetectorCompat mDetector;
    private ImageView imgView1;
  //  private TextView textViewQ;
    SeekBar sb;
    private static final int SWIPE_THRESHOLD = 100;
    private static final int SWIPE_VELOCITY_THRESHOLD = 100;
    int index = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.controlandevents);
        description(0);

        sb = (SeekBar) findViewById(R.id.seekBar);
        sb.setProgress(75);
        imgView1 = (ImageView) findViewById(R.id.imgView);

        sb.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar sb, int progress, boolean fromUser) {
               // textViewQ.setText(Integer.toString(progress));
                ViewGroup.LayoutParams params = imgView1.getLayoutParams();
                params.width = progress * 5;
                params.height = progress * 5;
                imgView1.setLayoutParams(params);

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }

        });


        mDetector = new GestureDetectorCompat(this, new GestureDetector.SimpleOnGestureListener() {

            public boolean onDown(MotionEvent event) {
                return true;
            }


            public boolean onFling(MotionEvent event1, MotionEvent event2, float velocityX, float velocityY) {
                MovieData m = new MovieData();
                HashMap movie = new HashMap();

                movie = m.getItem(index);
               // int s = (int) movie.get("image");


                float diffY = event1.getY() - event2.getY();
                float diffX = event1.getX() - event2.getX();
                if (Math.abs(diffX) > Math.abs(diffY)) {
                    if (Math.abs(diffX) > SWIPE_THRESHOLD && Math.abs(velocityX) > SWIPE_VELOCITY_THRESHOLD) {
                        if (diffX > 0) {
                            if (index < 29) {
                                description(index++);
                            }
                        } else {
                            if (index > 1) {
                                description(index--);
                            }
                        }

                    }
                }



                return true;
            }
        });




        findViewById(R.id.imgView).setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                sb.setProgress(75);
            return true;
            } });


        findViewById(R.id.imgView).setOnClickListener(this);


    }
    RelativeLayout mRelative;
    public void onClick(View v)
    {
        Toast.makeText(getApplicationContext(),"This is movie image",Toast.LENGTH_LONG).show();

         mRelative = (RelativeLayout) findViewById(R.id.cae);
        Snackbar snackbar = Snackbar.make(mRelative, "Welcome to Cybertron", Snackbar.LENGTH_LONG);
        snackbar.show();

    }


    public void description(int index)
    {
        HashMap movie = new HashMap();
        MovieData m = new MovieData();

        movie = m.getItem(index);
        int s = (int) movie.get("image");

        TextView text = (TextView) findViewById(R.id.textView);
        text.setText("\n"+"Name: "+(String)movie.get("name"));
        text.append("\n"+"Cast: "+(String)movie.get("stars"));
        text.append("\n"+"Description: " +(String)movie.get("description"));
        text.append("\n"+"Length: "+(String)movie.get("length"));
        text.append("\n"+"url: "+(String)movie.get("url"));
        ImageView imgView = (ImageView)findViewById(R.id.imgView);
        imgView.setImageResource(s);

    }

     @Override
     public boolean onTouchEvent (MotionEvent event){
         this.mDetector.onTouchEvent(event);
         return super.onTouchEvent(event);
     }

}











