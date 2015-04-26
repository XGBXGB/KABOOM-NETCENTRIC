package com.example.christiangabriel.training;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.DialogFragment;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.NumberPicker;

/**
 * Created by Christian Gabriel on 3/6/2015.
 */
public class SettingsDialog extends DialogFragment{
    private NumberPicker ip;
    private NumberPicker ap;
    private String[] s= {"Yes", "No"};
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder theDialog = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View rootView = inflater.inflate(R.layout.settings_dialog, null);
        theDialog.setView(rootView);

        ap = (NumberPicker) rootView.findViewById(R.id.number_picker);
        ap.setMaxValue(100);
        ap.setMinValue(0);
        ip = (NumberPicker) rootView.findViewById(R.id.age_picker);

        ip.setMinValue(0);
        ip.setMaxValue(s.length-1);
        ip.setDisplayedValues(s);


        Button confirm = (Button) rootView.findViewById(R.id.confirm_button);
        confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                SharedPreferences sharedPreferences = getActivity().getSharedPreferences("Profile",Context.MODE_PRIVATE);
                SharedPreferences.Editor editor = sharedPreferences.edit();

                editor.putInt("age", ap.getValue());
                editor.putString("insomniac", s[ip.getValue()]);
                editor.commit();
                dismiss();
            }
        });

        Button cancel = (Button) rootView.findViewById(R.id.cancel_button);
        cancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                dismiss();
            }
        });

        return theDialog.create();
    }
}
