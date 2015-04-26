package com.example.christiangabriel.training;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Christian Gabriel on 2/4/2015.
 */
public class SongAdapter extends ArrayAdapter<String>{

    public SongAdapter (Context context, ArrayList<String> music){
        super(context, R.layout.music_panel, music);
    }

    public View getView(int position, View convertView, ViewGroup parent) {

        // The LayoutInflator puts a layout into the right View
        LayoutInflater theInflater = LayoutInflater.from(getContext());
        //get title and artist views
        View theView = theInflater.inflate(R.layout.music_panel, parent, false);
        TextView songView = (TextView) theView.findViewById(R.id.song_title);
        TextView artistView = (TextView) theView.findViewById(R.id.song_artist);
        //get song using position
        //Song currSong = songs.get(position);
        //get title and artist strings
        songView.setText(getItem(position));
        artistView.setText("Kaboom");

        return theView;

    }
}
