package com.example.christiangabriel.training;

import android.app.AlarmManager;
import android.app.DialogFragment;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.provider.Settings;
import android.support.v7.app.ActionBarActivity;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;

import java.util.Calendar;


public class MainActivity extends ActionBarActivity  {

    private static final long REPEAT_TIME = 1000*60;
    private PendingIntent pendingIntent;
    private AlarmManager alarmManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SharedPreferences sharedPreferences = getSharedPreferences("Profile", MODE_PRIVATE);
        Intent alarmIntent = new Intent(this, AlarmReceiver.class);

        pendingIntent = PendingIntent.getBroadcast(this,0,alarmIntent,0);

        alarmManager = (AlarmManager) getSystemService(Context.ALARM_SERVICE);
        int interval = 1000;

        alarmManager.setRepeating(AlarmManager.RTC_WAKEUP, System.currentTimeMillis(), interval, pendingIntent);

        //

        //

//        Calendar cal = Calendar.getInstance();
//        Intent intent = new Intent(this, AlarmReceiver.class);
//        PendingIntent pintent = PendingIntent.getService(this,0,intent,0);
//        AlarmManager alarm = (AlarmManager) getSystemService(())
        //startService(new Intent(this, MyService.class));

//        Intent i = new Intent(MainActivity.this, DummyActivity.class);
//        startActivity(i);





        //startActivity(new Intent(this, RefreshScreen.class));

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }
    }



    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    public void openDialog(View view) {
        DialogFragment myFragment = new SettingsDialog();
        myFragment.show(getFragmentManager(), "DialogManager");
    }

    public void exit(View view){
        android.os.Process.killProcess(android.os.Process.myPid());
    }

    public void openMusic(View view){
        Intent i = new Intent(MainActivity.this, MusicActivity2.class);
        startActivity(i);
    }

    public void enterSleepMode(View view){
        Intent i = new Intent(MainActivity.this, SleepActivity.class);
        startActivity(i);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {
            View rootView = inflater.inflate(R.layout.fragment_main, container, false);


            return rootView;
        }

    }
}
