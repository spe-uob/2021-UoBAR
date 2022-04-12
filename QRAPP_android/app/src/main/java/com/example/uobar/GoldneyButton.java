package com.example.uobar;

import android.content.Intent;
import android.os.Bundle;
import android.speech.tts.TextToSpeech;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Locale;
import java.util.Scanner;

public class GoldneyButton extends AppCompatActivity {

   TextView goldneyInfo;
   ImageButton  goldneyBttn,next,back, audio;
   TextToSpeech t2s;


   @Override
    protected  void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.garden_info);
       goldneyInfo =findViewById(R.id.gardenInfoID);
       goldneyBttn =findViewById(R.id.RoyalID);

       audio = (ImageButton)findViewById(R.id.audio);


       goldneyInfo.setText("The text which contains information about the Goldney garden");

       next = findViewById(R.id.Next_scan);
       next.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),Scan_Page.class)));


       t2s = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
               if(status != TextToSpeech.ERROR) {
                  t2s.setLanguage(Locale.UK);
               }
            }
         });
         audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String toSpeak = goldneyInfo.getText().toString();
               Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
               t2s.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
         });
      }

      public void onPause(){
         if(t2s !=null){
            t2s.stop();
            t2s.shutdown();
         }
         super.onPause();
      }


}

