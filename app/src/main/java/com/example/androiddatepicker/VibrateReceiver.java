package com.example.androiddatepicker;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.widget.Toast;

public class VibrateReceiver extends BroadcastReceiver {
	AudioManager myAudioManager;
	@Override
	public void onReceive(Context arg0, Intent arg1) {
		myAudioManager = (AudioManager)arg0.getSystemService(Context.AUDIO_SERVICE);
		myAudioManager.setRingerMode(AudioManager.RINGER_MODE_VIBRATE);
		Toast.makeText(arg0,"Now in Vibrate Mode",Toast.LENGTH_LONG).show();
	}
}
