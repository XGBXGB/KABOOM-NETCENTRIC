package com.example.christiangabriel.training;

import android.content.ContentResolver;
import android.database.Cursor;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.support.v7.app.ActionBarActivity;
import android.support.v7.app.ActionBar;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.os.Build;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.MediaController;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Field;
import java.sql.SQLOutput;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.concurrent.ThreadLocalRandom;

import android.os.IBinder;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.view.MenuItem;
import android.view.View;



public class MusicActivity2 extends ActionBarActivity implements MediaController.MediaPlayerControl{

    private ArrayList<String> songList;
    private ListView songView;
    private MediaPlayer mediaPlayer;
    private MediaController controller;
    private Handler handler = new Handler();
    private int currentSong;

    //private MusicService musicSrv;
    private Intent playIntent;
    private boolean musicBound=false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_music_activity2);

        songView = (ListView) findViewById(R.id.song_list);
        songList = new ArrayList<String>();
        getSongList();

        SongAdapter songAdt = new SongAdapter(this, songList);
        songView.setAdapter(songAdt);

        mediaPlayer = new MediaPlayer();
        controller = new MediaController(this, false);
        controller.setMediaPlayer(this);
        controller.setAnchorView(findViewById(R.id.audio_view));

        String audioFile = "";
        try {
            mediaPlayer.setDataSource(audioFile);
            mediaPlayer.prepare();
        } catch (Exception e) {

        }

        mediaPlayer.setOnPreparedListener(new MediaPlayer.OnPreparedListener() {
            public void onPrepared(MediaPlayer mp) {
                handler.post(new Runnable() {
                    public void run() {
                        controller.show(10000);
                        mediaPlayer.start();
                    }
                });
            }
        });

        songView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                currentSong = position;
                playSong(songList.get(position));
                controller.show(0);
            }
        });

        controller.setPrevNextListeners(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                //next
                if(currentSong == songList.size()-1)
                {
                    currentSong = 0;
                }
                else
                {
                    currentSong ++;
                }
                playSong(songList.get(currentSong));
                controller.show(0);
            }
        }, new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                //prev
                if(currentSong == 0)
                {
                    currentSong = songList.size() - 1;
                }
                else
                {
                    currentSong --;
                }
                playSong(songList.get(currentSong));
                controller.show(0);
            }
        });
    }

    public void onDestroy() {
        super.onDestroy();
        mediaPlayer.stop();
        mediaPlayer.release();
    }

//    public void onPrepared(MediaPlayer mediaPlayer) {
//        controller.setMediaPlayer(this);
//        controller.setAnchorView(findViewById(R.id.audio_view));
//
//        handler.post(new Runnable() {
//            public void run() {
//                controller.setEnabled(true);
//                controller.show();
//            }
//        });
//    }

    @Override
    public boolean canPause() {
        return true;
    }

    @Override
    public boolean canSeekBackward() {
        return false;
    }

    @Override
    public boolean canSeekForward() {
        return false;
    }

    @Override
    public int getBufferPercentage() {
        int percentage = (mediaPlayer.getCurrentPosition() * 100) / mediaPlayer.getDuration();

        return percentage;
    }

    @Override
    public int getCurrentPosition() {
        return mediaPlayer.getCurrentPosition();
    }

    @Override
    public int getDuration() {
        return mediaPlayer.getDuration();
    }

    @Override
    public boolean isPlaying() {
        return mediaPlayer.isPlaying();
    }

    @Override
    public void pause() {
        if(mediaPlayer.isPlaying())
            mediaPlayer.pause();
    }

    @Override
    public void seekTo(int pos) {
        mediaPlayer.seekTo(pos);
    }

    @Override
    public void start() {
        mediaPlayer.start();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        controller.show();

        return false;
    }


    @Override
    public int getAudioSessionId() {
        return 0;
    }


    public void getSongList() {
        ContentResolver musicResolver = getContentResolver();
        Uri musicUri = android.provider.MediaStore.Audio.Media.EXTERNAL_CONTENT_URI;
        Cursor musicCursor = musicResolver.query(musicUri, null, null, null, null);

        getRaw();
    }

    public void getRaw(){
        Field[] fields = R.raw.class.getFields();
        songList = new ArrayList();
        for(int i=0; i<fields.length; i++){
            songList.add(fields[i].getName());
        }
    }

    public int songId=0;
    public void playSong(String s){
        if (mediaPlayer != null) {
            mediaPlayer.stop();
            mediaPlayer.release();
            mediaPlayer = null;
        }
        songId = getAllResourceIDs(R.raw.class, s);
        mediaPlayer = MediaPlayer.create(MusicActivity2.this,songId);
        try
        {
            mediaPlayer.prepare();
        }
        catch (IllegalStateException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        mediaPlayer.start();

        //Called when the song completes.....
        mediaPlayer.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                                                public void onCompletion(MediaPlayer mp) {
                                                    //Get next song and play again continuosly.
                                                }
                                            }
            );
    }

    public static int getAllResourceIDs(Class c, String song) throws IllegalArgumentException
    {
        //System.out.println("inside HashMap"+ song);
        HashMap resmap = new HashMap();
        java.lang.reflect.Field[] fields = c.getFields();
        try
        {
            for(int i = 0; i < fields.length; i++)
            {
                if(song != null)
                    if(fields[i].getName().startsWith(song))
                        resmap.put(fields[i].getName(), fields[i].getInt(null));
                    else
                        resmap.put(fields[i].getName(), fields[i].getInt(null));
            }
        } catch (Exception e)
        {
            throw new IllegalArgumentException();
        }
        Integer one = (Integer) resmap.get(song);
        int songid = one.intValue();
        return songid;
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_music, menu);
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

}
