package com.example.christiangabriel.training;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Christian Gabriel on 2/4/2015.
 */
public class MyAdapter extends ArrayAdapter<String>{

    public MyAdapter (Context context, ArrayList<String> days){
        super(context, R.layout.list_panel, days);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // The LayoutInflator puts a layout into the right View
        LayoutInflater theInflater = LayoutInflater.from(getContext());

        // inflate takes the resource to load, the parent that the resource may be
        // loaded into and true or false if we are loading into a parent view.
        View theView = theInflater.inflate(R.layout.list_panel, parent, false);

        // We retrieve the text from the array
//        String[] dayTime = getItem(position).split(" ");
//        String day = dayTime[0];
//        String time = dayTime[1];

        // Get the TextView we want to edit
        TextView theTextView = (TextView) theView.findViewById(R.id.list_textview1);
        // Put the next TV Show into the TextView
        theTextView.setText(getItem(position));

        // Get the ImageView in the layout

        // We can set a ImageView like this

        return theView;

    }
}
