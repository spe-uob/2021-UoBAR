package com.example.uobar;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Audio extends AppCompatActivity {
    MediaPlayer player;
   // String path = QRScanner.getPath();
    //String path1 = "..\\raw\\test.mp3";

    //int resID=getResources().getIdentifier("test", "raw", getPackageName());
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);

    }
    public void play(View v) {
        if(player == null){
            player = MediaPlayer.create(this,R.raw.test);
            player.setOnCompletionListener(new MediaPlayer.OnCompletionListener() {
                @Override
                public void onCompletion(MediaPlayer mp) {
                    stopPlaying();
                }
            });
        }
        player.start();
    }
    public void pause(View v){
        if(player != null){
            player.pause();
        }
    }
    public void stop(View v){
        stopPlaying();
    }
    public void stopPlaying(){
        if(player != null){
            player.release();
            player = null;
        }

    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlaying();
    }
}