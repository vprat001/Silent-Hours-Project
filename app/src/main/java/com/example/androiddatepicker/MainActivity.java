package com.example.androiddatepicker;

import java.util.Calendar;

import android.media.AudioManager;
import android.os.Bundle;
import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

public class MainActivity extends Activity{
	
	DatePicker pickerDate;
	TimePicker pickerTime;
	Button buttonSetAlarm;
	Button buttonSetSilent;
	TextView info;
	AudioManager myAudioManager;
	final static int RQS_1 = 1;
	Button next;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		myAudioManager = (AudioManager)getSystemService(Context.AUDIO_SERVICE);
		info = (TextView)findViewById(R.id.info);
		pickerDate = (DatePicker)findViewById(R.id.pickerdate);
		pickerTime = (TimePicker)findViewById(R.id.pickertime);
		buttonSetAlarm = (Button)findViewById(R.id.setvibrate);
		buttonSetSilent = (Button)findViewById(R.id.setsilent);
		next = (Button)findViewById(R.id.nextbutton);
		Calendar now = Calendar.getInstance();

		pickerDate.init(
				now.get(Calendar.YEAR),
				now.get(Calendar.MONTH),
				now.get(Calendar.DAY_OF_MONTH),
				null);
		
		pickerTime.setCurrentHour(now.get(Calendar.HOUR_OF_DAY));
		pickerTime.setCurrentMinute(now.get(Calendar.MINUTE));

		buttonSetAlarm.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View arg0) {
				Calendar current = Calendar.getInstance();

				Calendar cal = Calendar.getInstance();
				cal.set(pickerDate.getYear(),
						pickerDate.getMonth(),
						pickerDate.getDayOfMonth(),
						pickerTime.getCurrentHour(),
						pickerTime.getCurrentMinute(),
						00);

				if (cal.compareTo(current) <= 0) {
					//The set Date/Time already passed
					Toast.makeText(getApplicationContext(),
							"Invalid Date/Time",
							Toast.LENGTH_LONG).show();
				} else {
					setAlarm(cal);
				}

			}
		});

		buttonSetSilent.setOnClickListener(new OnClickListener(){
			@Override
			public void onClick(View arg0) {
				Calendar current = Calendar.getInstance();

				Calendar cal = Calendar.getInstance();
				cal.set(pickerDate.getYear(),
						pickerDate.getMonth(),
						pickerDate.getDayOfMonth(),
						pickerTime.getCurrentHour(),
						pickerTime.getCurrentMinute(),
						00);

				if(cal.compareTo(current) <= 0){
					//The set Date/Time already passed
					Toast.makeText(getApplicationContext(),
							"Invalid Date/Time",
							Toast.LENGTH_LONG).show();
				}else{
					setSilent(cal);
				}

			}
		});

	}


	private void setAlarm(Calendar targetCal){
		info.setText("\n\n"
				+ "Vibrate Mode is set on " + targetCal.getTime() + "\n"
				+ "\n");
		Intent intent = new Intent(getBaseContext(), VibrateReceiver.class);
		PendingIntent pendingIntent = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent, 0);
		AlarmManager alarmManager = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent);
		Log.d("MainActivity", "setAlarm()");
	}


	public void second(View v)
	{
		startActivity(new Intent(MainActivity.this, Main2Activity.class));
	}

	private void setSilent(Calendar targetCal){
		info.setText("\n\n"
				+ "Silent Mode is set on " + targetCal.getTime() + "\n"
				+ "\n");
		Intent intent1 = new Intent(getBaseContext(), SilentReceiver.class);
		PendingIntent pendingIntent1 = PendingIntent.getBroadcast(getBaseContext(), RQS_1, intent1, 0);
		AlarmManager alarmManager1 = (AlarmManager)getSystemService(Context.ALARM_SERVICE);
		alarmManager1.set(AlarmManager.RTC_WAKEUP, targetCal.getTimeInMillis(), pendingIntent1);
		Log.d("MainActivity", "setSilent()");
	}

}



