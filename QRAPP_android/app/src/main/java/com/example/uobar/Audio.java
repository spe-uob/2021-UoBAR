package com.example.uobar;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Audio extends AppCompatActivity {
    ImageButton back;
    MediaPlayer player;

    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        back = findViewById(R.id.audio_back);
        back.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),QRScanner.class)));


    }
    public void play(View v) throws IOException {
        if(player == null){
            Intent intent = getIntent();
            String path = intent.getStringExtra("audioPath");
            player = new MediaPlayer();
            AssetFileDescriptor afd;
            afd = getAssets().openFd(path);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
            afd.close();
            player.setOnCompletionListener(mp -> stopPlaying());
            player.prepare();
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