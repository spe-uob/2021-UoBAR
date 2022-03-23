package com.example.uobar;

import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageButton;
import android.widget.SeekBar;

import androidx.appcompat.app.AppCompatActivity;

import java.io.IOException;

public class Audio extends AppCompatActivity {
    ImageButton back;
    MediaPlayer player;
    SeekBar seekBar;
    Runnable runnable;
    Handler handler;
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_audio);
        back = findViewById(R.id.audio_back);
        back.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),QRScanner.class)));
        seekBar = findViewById(R.id.seekBar);

    }
    public void play(View v) throws IOException {
        if(player == null){
            Intent intent = getIntent();
            String path = intent.getStringExtra("audioPath");
            player = new MediaPlayer();
            handler = new Handler();
            seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
                @Override
                public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                    if(fromUser){
                        player.seekTo(progress);
                        seekBar.setProgress(progress);
                    }
                }

                @Override
                public void onStartTrackingTouch(SeekBar seekBar) {

                }

                @Override
                public void onStopTrackingTouch(SeekBar seekBar) {

                }
            });
            AssetFileDescriptor afd;
            afd = getAssets().openFd(path);
            player.setDataSource(afd.getFileDescriptor(), afd.getStartOffset(),afd.getLength());
            afd.close();
            player.setOnCompletionListener(mp -> stopPlaying());
            player.prepare();

        }
        seekBar.setMax(player.getDuration());
        player.start();
        updateSeekBar();
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
            handler = null;
            runnable = null;
        }


    }

    @Override
    protected void onStop() {
        super.onStop();
        stopPlaying();
    }

    public void updateSeekBar(){
        int currPos = player.getCurrentPosition();
        seekBar.setProgress(currPos);
        runnable = new Runnable() {
            @Override
            public void run() {
                updateSeekBar();
            }
        };
        handler.postDelayed(runnable,1000);
    }
}