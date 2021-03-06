package com.example.uobar;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import android.speech.tts.TextToSpeech;
import android.widget.Toast;

import java.util.Locale;

import androidx.appcompat.app.AppCompatActivity;

public class RoyalFortButton extends AppCompatActivity {

    TextView royalInfo;
    ImageButton royalBttn, next_scan, audio, back_main;
    TextToSpeech textToS;


    @Override
    protected  void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        setContentView(R.layout.garden_info);
        royalInfo =findViewById(R.id.gardenInfoID);
        royalBttn =findViewById(R.id.GoldneyID);


        royalInfo.setText("Following a failed attempt to develop the gardens for housing, at the end of the eighteenth century, renowned landscape architect Humphry Repton was commissioned to reinstate a garden in the 'English Landscape' fashion. Repton produced a design which filled in the unsightly excavations; created an undulating lawn and screened the undesirable - or framed the desirable – views. A high wall surrounds and retains the garden. This would have acted as a 'ha-ha' to gain what, at the time, would have been unspoiled vistas.");

        next_scan = findViewById(R.id.Next_scan);
        next_scan.setOnClickListener(view -> {
            Intent intent = new Intent();
            intent.setClass(RoyalFortButton.this, MapsActivity.class);
            intent.putExtra("area", "RylFort");
            startActivity(intent);
        });

        back_main = findViewById(R.id.info_back);
        back_main.setOnClickListener(v -> startActivity(new Intent(getApplicationContext(),MainActivity.class)));


        audio = findViewById(R.id.audio);
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
                String toSpeak = royalInfo.getText().toString();
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

