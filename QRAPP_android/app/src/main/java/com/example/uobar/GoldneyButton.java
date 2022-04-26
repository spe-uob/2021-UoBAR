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
   ImageButton  goldneyBttn,next_scan,back_main, audio;
   TextToSpeech textToS;


   @Override
    protected  void onCreate(Bundle savedInstanceState) {

       super.onCreate(savedInstanceState);
       setContentView(R.layout.garden_info);
       goldneyInfo =findViewById(R.id.gardenInfoID);
       goldneyBttn =findViewById(R.id.RoyalID);

       audio = findViewById(R.id.audio);


       goldneyInfo.setText("The text which contains information about the Goldney garden");

       next_scan = findViewById(R.id.Next_scan);
       next_scan.setOnClickListener(view -> {
           Intent intent = new Intent();
           intent.setClass(GoldneyButton.this, MapsActivity.class);
           intent.putExtra("area", "Goldeney");
           startActivity(intent);
       });

       back_main = findViewById(R.id.info_back);
       back_main.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));



       textToS = new TextToSpeech(getApplicationContext(), new TextToSpeech.OnInitListener() {
            @Override
            public void onInit(int status) {
               if(status != TextToSpeech.ERROR) {
                   textToS.setLanguage(Locale.UK);
               }
            }
         });
         audio.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
               String toSpeak = goldneyInfo.getText().toString();
               Toast.makeText(getApplicationContext(), toSpeak,Toast.LENGTH_SHORT).show();
               textToS.speak(toSpeak, TextToSpeech.QUEUE_FLUSH, null);
            }
         });
      }

      public void onPause(){
         if(textToS !=null){
             textToS.stop();
             textToS.shutdown();
         }
         super.onPause();
      }


}

