package com.example.christiangabriel.training;

/**
 * Created by Christian Gabriel on 3/7/2015.
 */

import android.app.Service;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.os.IBinder;
import android.provider.Settings;
import android.util.Log;
import android.view.WindowManager;
import android.widget.Toast;

import java.util.Calendar;


public class AlarmReceiver extends BroadcastReceiver {
    public static final String DEFAULT = "N/A";
    public static boolean flag = true;
    public static boolean toastFlag = true;

    @Override
    public void onReceive(Context arg0, Intent arg1) {
        SharedPreferences sharedPreferences = arg0.getSharedPreferences("Profile", arg0.MODE_PRIVATE);
        Calendar c = Calendar.getInstance();
        int tomo = c.get(Calendar.DAY_OF_WEEK);
        String alarm;
        String[] time;
        int startHours, startMins;
        int currHours, currMins;
        int endHours,endMins;

        alarm = sharedPreferences.getString(String.valueOf(tomo), DEFAULT);

        if (!alarm.equals(DEFAULT)) {
            alarm = alarm.replaceAll(" ", "");
            time = alarm.split(":");
            startHours = Integer.parseInt(time[0])-8;
            startMins = Integer.parseInt(time[1]);

            currHours = c.get(Calendar.HOUR_OF_DAY);
            currMins = c.get(Calendar.MINUTE);

            endHours = Integer.parseInt(time[0]);
            endMins = Integer.parseInt(time[1])+1;

            if ( (startHours <= currHours && currHours <= endHours) && (startMins <= currMins && currMins <= endMins)){
                Settings.System.putInt(arg0.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_MANUAL);
                Settings.System.putInt(arg0.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, 3);

                AudioManager audioManager = (AudioManager) arg0.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_SILENT);
            }
            else{
                Settings.System.putInt(arg0.getContentResolver(),
                        Settings.System.SCREEN_BRIGHTNESS_MODE, Settings.System.SCREEN_BRIGHTNESS_MODE_AUTOMATIC);
                Settings.System.putInt(arg0.getContentResolver(), Settings.System.SCREEN_BRIGHTNESS, -1);

                AudioManager audioManager = (AudioManager) arg0.getApplicationContext().getSystemService(Context.AUDIO_SERVICE);
                audioManager.setRingerMode(AudioManager.RINGER_MODE_NORMAL);
            }

            if(startHours==currHours && startMins == currMins) {
                Toast.makeText(arg0, "SLEEP MODE", Toast.LENGTH_LONG).show();
            }
        }


    }
}
