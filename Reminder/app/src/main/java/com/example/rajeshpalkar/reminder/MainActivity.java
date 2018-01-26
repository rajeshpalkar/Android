package com.example.rajeshpalkar.reminder;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Intent;
import java.util.Calendar;
import android.os.Build;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    String AM_PM= null;
    String formattedMin=null;
    String formattedHour=null;
    AlarmManager alarmManager;
    PendingIntent pendingIntent;
    TextView alarm;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button notification = (Button) findViewById(R.id.button);
        Button cancel = (Button) findViewById(R.id.button2);
        alarm = (TextView) findViewById(R.id.textView2);

        final Intent intent = new Intent(getApplicationContext(),Notification_Receiver.class);

        notification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                TimePicker tp = (TimePicker) findViewById(R.id.timePicker);
                int hour = tp.getHour();
                int min = tp.getMinute();
                System.out.println("hour :"+hour);
                System.out.println("minu :"+min);

                calendar.setTimeInMillis(System.currentTimeMillis());
                calendar.set(Calendar.HOUR_OF_DAY,hour);
                calendar.set(Calendar.MINUTE,min);
                calendar.set(Calendar.SECOND,10);
                calendar.set(Calendar.AM_PM,hour>12?Calendar.PM:Calendar.AM);


                if(min<10)
                {
                 formattedMin = String.format("%02d", min);
                }
                else
                {
                    formattedMin = Integer.toString(min);
                }

                if(hour<10)
                {
                    formattedHour = String.format("%02d", hour);
                }
                else
                {
                    formattedHour = Integer.toString(hour);
                }

                System.out.println();
                if(calendar.getTimeInMillis()-System.currentTimeMillis()<0)
                    calendar.add(Calendar.DAY_OF_YEAR,1);

                Toast.makeText(MainActivity.this, "Alarm set to "+formattedHour+":"+formattedMin+" ", Toast.LENGTH_SHORT).show();
                alarm.setText(formattedHour+":"+formattedMin);


             //   Intent intent = new Intent(MY_ACTION);

                intent.putExtra("extra","alarm on");
               // intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                pendingIntent = PendingIntent.getBroadcast(getApplicationContext(),100,intent,PendingIntent.FLAG_UPDATE_CURRENT);
                //pendingIntent.add
                alarmManager = (AlarmManager) getSystemService(ALARM_SERVICE);
                alarmManager.setAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent);
              //  alarmManager.setAlarmClock(new AlarmManager.AlarmClockInfo(calendar.getTimeInMillis(), pendingIntent), pendingIntent);
             //   alarmManager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),pendingIntent) ;
               // alarmManager.setRepeating(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),AlarmManager.INTERVAL_DAY,pendingIntent) ;
            }
        });

        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                alarmManager.cancel(pendingIntent);
                Toast.makeText(MainActivity.this, "Alarm set to "+formattedHour+":"+formattedMin+" was cancelled.", Toast.LENGTH_SHORT).show();
            }
        });


    }
}
