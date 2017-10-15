package com.example.androiddatepicker;


import android.annotation.TargetApi;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

import static java.util.Calendar.getInstance;

public class Main2Activity extends MainActivity {

    DatePicker DatePicker;
    TimePicker TimePicker;
    Button setNormal;
    TextView info2;
    AudioManager myAudioManager;

    final static int RQS_1 = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        DatePicker = (DatePicker)findViewById(R.id.datePicker);
        TimePicker = (TimePicker)findViewById(R.id.timePicker);

        myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
        setNormal = (Button)findViewById(R.id.setNormal);
        info2 = (TextView)findViewById(R.id.Info2);
        Calendar now = Calendar.getInstance();


        DatePicker.init(
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH),
                null);

        TimePicker.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
        TimePicker.setCurrentMinute(now.get(Calendar.MINUTE));

        setNormal.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View arg0) {
                Calendar current = Calendar.getInstance();

                Calendar cal = Calendar.getInstance();
                cal.set(pickerDate.getYear(),
                        pickerDate.getMonth(),
                        pickerDate.getDayOfMonth(),
                        TimePicker.getCurrentHour(),
                        TimePicker.getCurrentMinute(),
                        00);

                if (cal.compareTo(current) <= 0) {
                    //The set Date/Time already passed
                    Toast.makeText(getApplicationContext(),
                            "Invalid Date/Time",
                            Toast.LENGTH_LONG).show();
                } else {
                    setNormal(cal);
                }

            }
        });

    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setNormal(Calendar targetCal){
        info2.setText("\n\n"
                + "Volume Mode is set on " + targetCal.getTime() + "\n"
                + "\n");
        Intent intent = new Intent(getBaseContext(), VolumeReceiver.class);
        PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
        AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
        alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
        Log.d("Main2Activity", "setNormal()");
    }
}
