package com.example.christiangabriel.training;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.TimePicker;
import android.widget.Toast;

import java.util.Calendar;

/**
 * Created by Christian Gabriel on 3/7/2015.
 */
public class AlarmDialog extends DialogFragment{
    private TimePicker tp;
    int hour;
    int minute;
    int position;
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        final View rootView = inflater.inflate(R.layout.alarm_dialog, null);
        theDialog.setView(rootView);

        position = getArguments().getInt("position");

        tp = (TimePicker) rootView.findViewById(R.id.time_picker);
        final Calendar c = Calendar.getInstance();
        hour = c.get(Calendar.HOUR_OF_DAY);
        minute = c.get(Calendar.MINUTE);
        tp.setCurrentHour(hour);
        tp.setCurrentMinute(minute);



        Button confirm = (Button) rootView.findViewById(R.id.confirm_alarm);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Profile", getActivity().getApplicationContext().MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putString(String.valueOf(position), String.valueOf(tp.getCurrentHour()) + ":" + String.valueOf(tp.getCurrentMinute()));
                Toast.makeText(getActivity().getApplicationContext(), String.valueOf(tp.getCurrentHour()) + ":" + String.valueOf(tp.getCurrentMinute()), Toast.LENGTH_LONG).show();
                editor.commit();

                dismiss();

                Intent i = new Intent(getActivity(), SleepActivity.class);
                startActivity(i);
            }
        });

        Button cancel = (Button) rootView.findViewById(R.id.cancel_alarm);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Profile", Context.MODE_PRIVATE);
//                SharedPreferences.Editor editor = sharedPreferences.edit();
//
//                editor.putInt("age", ap.getValue());
//                editor.putString("insomniac", s[ip.getValue()]);
//                editor.commit();
                dismiss();
            }
        });

        return theDialog.create();
    }


}
